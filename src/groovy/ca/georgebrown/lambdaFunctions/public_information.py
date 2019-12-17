import json
import datetime
import time
import os
import dateutil.parser
import escalate
import logging
from botocore.vendored import requests
import boto3
import base64
from botocore.exceptions import ClientError
from boto3.dynamodb.conditions import Key, Attr
import decimal 

logger = logging.getLogger()
logger.setLevel(logging.ERROR)

class DecimalEncoder(json.JSONEncoder):
    
    def default(self, o):
        if isinstance(o, decimal.Decimal):
            return str(o)
        return super(DecimalEncoder, self).default(o)


# --- Helpers that build all of the responses ---
def elicit_slot(session_attributes, intent_name, slots, slot_to_elicit, message):
    return {
        'sessionAttributes': session_attributes,
        'dialogAction': {
            'type': 'ElicitSlot',
            'intentName': intent_name,
            'slots': slots,
            'slotToElicit': slot_to_elicit,
            'message': message
        }
    }


def confirm_intent(session_attributes, intent_name, slots, message):
    return {
        'sessionAttributes': session_attributes,
        'dialogAction': {
            'type': 'ConfirmIntent',
            'intentName': intent_name,
            'slots': slots,
            'message': message
        }
    }


def close(session_attributes, fulfillment_state, message):
    response = {
        'sessionAttributes': session_attributes,
        'dialogAction': {
            'type': 'Close',
            'fulfillmentState': fulfillment_state,
            'message': message
        }
    }

    return response


def delegate(session_attributes, slots):
    return {
        'sessionAttributes': session_attributes,
        'dialogAction': {
            'type': 'Delegate',
            'slots': slots
        }
    }


# --- Helper Functions ---
def safe_int(n):
    if n is not None:
        return int(n)
    return n


def try_ex(func):
    try:
        return func()
    except KeyError:
        return None
    

def get_secret():
    secret_name = "TestChatbot"
    region_name = "us-east-1"

    # Create a Secrets Manager client
    session = boto3.session.Session()
    client = session.client(
        service_name='secretsmanager',
        region_name=region_name
    )
    try:
        get_secret_value_response = client.get_secret_value(
            SecretId=secret_name
        )
        logger.debug(get_secret_value_response)
    except ClientError as e:
        if e.response['Error']['Code'] == 'DecryptionFailureException':
            # Secrets Manager can't decrypt the protected secret text using the provided KMS key.
            # Deal with the exception here, and/or rethrow at your discretion.
            raise e
        elif e.response['Error']['Code'] == 'InternalServiceErrorException':
            # An error occurred on the server side.
            # Deal with the exception here, and/or rethrow at your discretion.
            raise e
        elif e.response['Error']['Code'] == 'InvalidParameterException':
            # You provided an invalid value for a parameter.
            # Deal with the exception here, and/or rethrow at your discretion.
            raise e
        elif e.response['Error']['Code'] == 'InvalidRequestException':
            # You provided a parameter value that is not valid for the current state of the resource.
            # Deal with the exception here, and/or rethrow at your discretion.
            raise e
        elif e.response['Error']['Code'] == 'ResourceNotFoundException':
            # We can't find the resource that you asked for.
            # Deal with the exception here, and/or rethrow at your discretion.
            raise e
    else:
        # Decrypts secret using the associated KMS CMK.
        # Depending on whether the secret is a string or binary, one of these fields will be populated.
        if 'SecretString' in get_secret_value_response:
            secret = get_secret_value_response['SecretString']
            return secret
        else:
            decoded_binary_secret = base64.b64decode(get_secret_value_response['SecretBinary'])


# checks if the given studnet id input exists on the database, and returns the matching name.
def check_student_id(intent_request):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    studentId = intent_request['currentIntent']['slots']['studentId']
    
    if(studentId):
        url = 'https://dmzmsa01.georgebrown.ca/ChatbotService/api/checkStudentId?studentId=' + studentId
        response = requests.get(url)
        data = response.json()
        if (data['success'] == True):
            session_attributes['GBCuserId'] = data['userId']
            session_attributes['GBCappId'] = data['appId']
            message = "Hi " + data['firstName'] + ", your student ID number has been saved for this chat session."
            return {
                'sessionAttributes': session_attributes,
                'dialogAction': {
                    'type': 'ElicitIntent',
                    'message': {
                        'contentType': 'PlainText',
                        'content': message
                    }, 
                    'responseCard': {
                        'version': 1,
                        'contentType': 'application/vnd.amazonaws.card.generic',
                        'genericAttachments': [
                            {
                                'buttons': [
                                    {
                                        'text': 'Continue',
                                        'value': 'help'
                                    }
                                ]
                            }
                        ]
                    }
                }
            }
        else:
            message = "We could not find any student matching with that ID number. Please try again with different student ID. You can type 'no id' if you only want to access our public questions."
            return close (
                session_attributes,
                'Fulfilled',
                {
                    'contentType': 'PlainText',
                    'content': message
                }
            )
    else:
        message = "You have not entered your student ID in correct format. Please revise and enter your student ID number. "
        return close (
            session_attributes,
            'Fulfilled',
            {
                'contentType': 'PlainText',
                'content': message
            }
        )

# returns tuition information by the given course code input from the database
def get_tuition_by_code(intent_request):
    secrets_resp = get_secret()
    secrets_data = json.loads(secrets_resp)
    username = secrets_data['username']
    password = secrets_data['password']
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    
    if(intent_request['currentIntent']['slots']['code']):
        code = intent_request['currentIntent']['slots']['code']
        url = 'https://dmzmsa01.georgebrown.ca/ChatbotService/api/getTuitionByCode?code=' + code
        response = requests.get(url)
        data = response.json()

        if(data['success'] == True):
            if(data['costs'] and data['costs'] != 'null'):
                for x in data['costs']:
                    message = 'Here is the tuition information for ' + code + '. '
                    message = message + 'For ' + x['termName'] + ', the estimated term fee is $' + x['cost'] + ' (CAD). '
            else:
                message = 'There was no cost information for ' + code + ' in the next three terms. '
            
            message = message + 'If you wish to look for more information, please visit https://www.georgebrown.ca/international/futurestudents/tuitionfees/'
            return close(session_attributes, 'Fulfilled', {'content': message, 'contentType': 'PlainText'})
    else:
        return {
            'sessionAttributes': session_attributes,
            'dialogAction': {
            'type': 'ElicitSlot',
            'intentName': intent_request['currentIntent']['name'],
            'slots': intent_request['currentIntent']['slots'],
            'slotToElicit': 'code',
            'message': {
                'contentType': 'PlainText',
                'content': 'Enter the four digit code of the program that you would like to get the tuition for.'
                },
            }
        }
    
    
# --- Intents ---
def dispatch(intent_request):
    try:
        session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
        logger.debug('dispatch userId={}, intentName={}'.format(intent_request['userId'], intent_request['currentIntent']['name']))
        intent_name = intent_request['currentIntent']['name']

        if intent_name == 'getTuitionByCode':
            escalate.write_to_intent_table(intent_request)
            return get_tuition_by_code(intent_request)
        elif intent_name == 'getStudentId':
            if(session_attributes['GBCuserId'] == ""):
                escalate.write_to_intent_table(intent_request)
                return check_student_id(intent_request)
            # If the user is already logged in, this intent pretend like it did not get invoked.
            else: 
                message = "Sorry, I don't understand. Could you rephrase? You can type 'email' to email someone directly."
                return close (
                    session_attributes,
                    'Fulfilled',
                    {
                        'contentType': 'PlainText',
                        'content': message
                    }
                )
        elif intent_name == 'noStudentId':
            if(session_attributes['GBCuserId'] == ""):
                escalate.write_to_intent_table(intent_request)
                message = "Ok. You can ask your questions now!"
                return close (
                    session_attributes,
                    'Fulfilled',
                    {
                        'contentType': 'PlainText',
                        'content': message
                    }
                )
            # If the user is already logged in, this intent pretend like it did not get invoked.
            else:
                message = "Sorry, I don't understand. Could you rephrase? You can type 'email' to email someone directly."
                return close (
                    session_attributes,
                    'Fulfilled',
                    {
                        'contentType': 'PlainText',
                        'content': message
                    }
                )
        else:
            raise Exception('Intent with name ' + intent_name + ' not supported')
    except Exception as e:
        print(e)


# --- Main handler ---
def lambda_handler(event, context):
    # By default, treat the user request as coming from the America/New_York time zone.
    os.environ['TZ'] = 'America/New_York'
    time.tzset()
    logger.debug('event.bot.name={}'.format(event['bot']['name']))
    session_attributes = event['sessionAttributes'] if event['sessionAttributes'] is not None else {}
    
    #update intent tracker
    session_attributes['currentIntent'] = event['currentIntent']['name'] 
    data = event['inputTranscript']
        
    if session_attributes['currentIntent'] in ['getTuitionByCode', 'getStudentId', 'noStudentId']:
        return dispatch(event)
    else:
        return escalate.write_to_intent_table(event)
