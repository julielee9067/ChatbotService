import json
import datetime
import time
import os
import dateutil.parser
import logging
from botocore.vendored import requests
import boto3
import base64
from botocore.exceptions import ClientError
from auth_user import auth_session
import escalate
from socket import *

logger = logging.getLogger()
logger.setLevel(logging.ERROR)


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
    """
    Safely convert n value to int.
    """
    if n is not None:
        return int(n)
    return n


def try_ex(func):
    """
    Call passed in function in try block. If KeyError is encountered return None.
    This function is intended to be used to safely access dictionary.

    Note that this function would have negative impact on performance.
    """

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


""" --- Functions that control the bot's behavior --- """
# modifies the application
def change_app(intent_request):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    authItem = auth_session(intent_request)
    
    if(type(authItem) == dict):
        return authItem
    
    if(str(session_attributes['authRole']) not in ['5', '4']):
            return close(session_attributes, 'Fulfilled', {'contentType': 'PlainText', 'content': 'You are not authorized to change applications.'})
    
    return close(session_attributes, 'Fulfilled', {'contentType': 'PlainText', 'content': 'The applicant has been changed to ' + session_attributes['authAppDetails']})


# returns the status of the user's application
def get_status(intent_request):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    secrets_resp = get_secret()
    secrets_data = json.loads(secrets_resp)
    username = secrets_data['username']
    password = secrets_data['password']
    authItem = auth_session(intent_request)
    
    if(type(authItem) == dict):
        return authItem
        
    if authItem == "true" :
        appId = session_attributes['GBCappId']
        url = 'https://dmzmsa01.georgebrown.ca/ChatbotService/api/getStatus?username=' + username + '&password=' + password + '&appId=' + appId
        
        response = requests.get(url)
        data = response.json()
        
        if (data['success'] == True):
            message = data['status']
        else:
            logger.debug('Incorrect Username and Password, check Secrets Manager')
    else:
        userId = intent_request['userId']
        if(userId):
            lexChatToken = intent_request['userId']
            message = "Please log in using your ID and password on the following link: https://dmzgrg01u.georgebrown.ca/gbcauth/login/index?lexChatToken={}".format(lexChatToken)
        else:
            message = "Cannot retrieve your chat token. Please try again later."
        
    return close(
        session_attributes,
        'Fulfilled',
        {
            'contentType': 'PlainText',
            'content': message
        }
    )
    

# returns the tuition for the program the user has applied for
def get_tuition_by_user(intent_request):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    secrets_resp = get_secret()
    secrets_data = json.loads(secrets_resp)
    username = secrets_data['username']
    password = secrets_data['password']
    message = ''
    authItem = auth_session(intent_request)
    
    if(type(authItem) == dict):
        return authItem
    if authItem == "true" :
        appId = session_attributes['GBCappId']
        url = 'https://dmzmsa01.georgebrown.ca/ChatbotService/api/getTuitionByUser?username=' + username + '&password=' + password + '&appId=' + appId
        response = requests.get(url)
        data = response.json()
        
        if (data['success'] == True):
            message = data['costs']
        else:
            logger.debug('Incorrect Username and Password, check Secrets Manager')
    else:
        userId = intent_request['userId']
        if(userId):
            lexChatToken = intent_request['userId']
            message = "Please log in using your ID and password on the following link: https://dmzgrg01u.georgebrown.ca/gbcauth/login/index?lexChatToken={}".format(lexChatToken)
        else:
            message = "Cannot retrieve your chat token. Please try again later."

    return close(
        session_attributes,
        'Fulfilled',
        {
            'contentType': 'PlainText',
            'content': message + 'If you did not find what you were looking for, you could check all of our international student tuition information on a following website: https://www.georgebrown.ca/international/futurestudents/tuitionfees/.'
        }
    ) 
   
# returns the tuition the user has paid for
def payment_check(intent_request):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    authItem = auth_session(intent_request)
   
    if(type(authItem) == dict):
        return authItem
    
    if authItem == "true" : 
        secrets_resp = get_secret()
        secrets_data = json.loads(secrets_resp)
        username = secrets_data['username']
        password = secrets_data['password']
        userId = session_attributes['GBCuserId']
 
        url = 'https://dmzmsa01.georgebrown.ca/ChatbotService/api/checkIfTuitionIsSent?username=' + username + '&password=' + password + '&userId=' + userId
        response = requests.get(url)
        data = response.json()
        
        if (data['success'] == True):
            message = data['message']
        else:
            message = "Whoops. Something went wrong"
    else:
        message = "Whoops. Something went wrong"

    return close(
        session_attributes,
        'Fulfilled',
        {
            'contentType': 'PlainText',
            'content': message
        }
    )


# --- Intents ---
def dispatch(intent_request):
    logger.debug('dispatch userId={}, intentName={}'.format(intent_request['userId'], intent_request['currentIntent']['name']))
    intent_name = intent_request['currentIntent']['name']
    try:
        escalate.write_to_intent_table(intent_request)
        if intent_name == 'getStatus':
            return get_status(intent_request)
        elif intent_name == 'changeApplicant': 
            return change_app(intent_request)
        elif intent_name == 'getTuitionByUser':
            return get_tuition_by_user(intent_request)
        
        raise Exception('Intent with name ' + intent_name + ' not supported')
    except Exception as e:
        print(e)


# --- Main handler ---
def lambda_handler(event, context):
    """
    Route the incoming request based on intent.
    The JSON body of the request is provided in the event slot.
    """
    # By default, treat the user request as coming from the America/New_York time zone.
    os.environ['TZ'] = 'America/New_York'
    time.tzset()
    logger.debug('event.bot.name={}'.format(event))
    
    return dispatch(event)
