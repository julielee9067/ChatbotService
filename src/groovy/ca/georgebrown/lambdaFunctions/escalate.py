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
from boto3.dynamodb.conditions import Key, Attr
from authUser import auth_session
import decimal
import dateutil.tz


# --- Bot functions ---
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


""" --- Dynamo functions --- """
def get_first_bubble_to_be_returned(session_attributes):
    #set -up table
    dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
    table = dynamodb.Table('AskGeorgeTranscriptV6')
    
    #set up query parameters
    now = datetime.datetime.now().strftime("%Y-%m-%d-%H-%M-%S") 
    before = (datetime.datetime.now() - datetime.timedelta(minutes = 60)).strftime("%Y-%m-%d-%H-%M-%S")
    token = session_attributes['GBCToken']
    orderString = session_attributes['order']
    orderMax = int(orderString, 10)
    numBubbles = 10
    orderMin = orderMax - numBubbles
    
    if(orderMin < 1):
        orderMin = 1
    
    response = table.query(
        ProjectionExpression="OrderBubble, Dialog, ChatMessage",
        KeyConditionExpression = Key('UserIdPrimaryKey').eq(token) & Key('OrderBubble').between(orderMin, orderMin))
    
    if(response['Items']):
        return "Every bubble after \'" + response['Items'][0]['ChatMessage'] + "\' will be e-mailed to an admission officer to further assist you. \nWould you rather directly talk to our admissions officer?"
    else:
        return "I will e-mail an admission officer that you had a problem, but there wasn't anything in the transcript."
    
    
def get_transcript(session_attributes):
    #set up table
    dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
    table = dynamodb.Table('AskGeorgeTranscriptV6')
    
    #set up query parameters
    now = datetime.datetime.now().strftime("%Y-%m-%d-%H-%M-%S") 
    before = (datetime.datetime.now() - datetime.timedelta(minutes = 60)).strftime("%Y-%m-%d-%H-%M-%S")
    token = session_attributes['GBCToken']
    orderString = session_attributes['order']
    orderMax = int(orderString, 10) - 2
    numBubbles = 19 # WHY IS THIS 19????
    orderMin = orderMax - numBubbles
    
    if(orderMin < 1):
        orderMin = 1
    if(orderMax < 1):
        orderMax = 1
        
    response = table.query(
        ProjectionExpression="OrderBubble, Dialog, ChatMessage",
        KeyConditionExpression = Key('UserIdPrimaryKey').eq(token) & Key('OrderBubble').between(orderMin, orderMax) )
    return response


def get_first_bubble_to_be_returned(session_attributes):
    #set up table
    dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
    table = dynamodb.Table('AskGeorgeTranscriptV6')
    
    #set up query parameters
    now = datetime.datetime.now().strftime("%Y-%m-%d-%H-%M-%S") 
    before = (datetime.datetime.now() - datetime.timedelta(minutes = 60)).strftime("%Y-%m-%d-%H-%M-%S")
    token = session_attributes['GBCToken']
    orderString = session_attributes['order']
    orderMax = int(orderString, 10) - 4
    numBubbles = 19
    orderMin = orderMax - numBubbles
    
    if(orderMin < 1):
        orderMin = 1

    response = table.query(
        ProjectionExpression="OrderBubble, Dialog, ChatMessage",
        KeyConditionExpression = Key('UserIdPrimaryKey').eq(token) &Key('OrderBubble').between(orderMin, orderMin))
    
    if(response['Items']):
        return "Every bubble after \"" + response['Items'][0]['ChatMessage'] + "\" will be e-mailed to an admission officer to further assist you. Would you rather directly send an e-mail to our admissions officer?"
    else:
        return "I will e-mail an admission officer that you had a problem, but there wasn't anything in the transcript."
    
    
def get_email(intent_request):
    sendee_email = "Julie.Lee2@georgebrown.ca"
    return sendee_email


def send_email(intent_request):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    authItem = auth_session(intent_request)
   
    if(type(authItem) == dict):
        return authItem
        
    response = get_transcript(session_attributes)
    numBubbles = str(response['Count'])
    userId = session_attributes['GBCuserId']
    Sender_name = "ChatBot"
    
    if(userId and authItem == 'true'):
        secrets_resp = get_secret()
        secrets_data = json.loads(secrets_resp)
        username = secrets_data['username']
        password = secrets_data['password']
        url = 'https://dmzmsa01.georgebrown.ca/ChatbotService/api/getUserInfoForEmail?userId=' + userId + '&username=' + username + '&password=' + password
        result_for_user = requests.get(url)
        data = result_for_user.json()

        if (data['success'] == True):
            user_email = data['email']
            user_first_name = data['firstName']
            user_last_name = data['lastName']
            user_banner_id = data['bannerId']
        else:
            user_email = ''
            user_first_name = ''
            user_last_name = ''
            user_banner_id = ''
        
        user_name = user_first_name + ' ' + user_last_name
        if(user_name != ' '):
            print(user_name)
        elif(intent_request['currentIntent']['slots']['userName'] != None):
            user_name = intent_request['currentIntent']['slots']['userName']
        else:
            return elicit_slot(session_attributes, intent_request['currentIntent']['name'],
                            intent_request['currentIntent']['slots'], 'userName', {'contentType': 'PlainText',
                                        'content': "Please provide your first name. "})
        
        Sender_email = "Julie.Lee2@georgebrown.ca"
    
        if(user_email != ''):
            print(user_email)
        elif(intent_request['currentIntent']['slots']['userEmail'] != None):
            user_email = intent_request['currentIntent']['slots']['userEmail']
        else:
            return elicit_slot(session_attributes, intent_request['currentIntent']['name'],
                            intent_request['currentIntent']['slots'], 'userEmail', {'contentType': 'PlainText',
                                        'content': "We noticed that we don't have your email address on our system. Please provide your email address."})
        
        RECIPIENT = get_email(intent_request)
        AWS_REGION = "us-east-1"
        SUBJECT = "ChatBot Transcript from " + user_first_name + " " + user_last_name
        BODY_TEXT = "Trying to send a transcript"
        BODY_HTML = """<html><body>
          <h1>Transcript of AskGeorgeBot From """ + user_banner_id + """:</h1>
          <br> Name: """ + user_first_name + " " + user_last_name + """
          <br> Email: """ + user_email + """
          <br>
          <br>
          <p> These are the last """ + numBubbles + """ messages from the conversation. </p>
          """
        x = 0
        
        while x < response['Count']:
            BODY_HTML = BODY_HTML + '<p>' + response['Items'][x]['Dialog'] + ': ' + response['Items'][x]['ChatMessage'] + '</p>'
            x+=1
        
        BODY_HTML = BODY_HTML + """</body></html>"""
        CHARSET = "UTF-8"
        client = boto3.client('ses',region_name= AWS_REGION)
        SENDER = "{} <{}>".format(Sender_name, Sender_email)
        
        try:
            response = client.send_email(
                Destination = {
                    'ToAddresses': [
                        RECIPIENT,
                    ],
                },
                Message = {
                    'Body': {
                        'Html': {
                            'Charset': CHARSET,
                            'Data': BODY_HTML,
                        },
                        'Text': {
                            'Charset': CHARSET,
                            'Data': BODY_TEXT,
                        },
                    },
                    'Subject': {
                        'Charset': CHARSET,
                        'Data': SUBJECT,
                    },
                },
                Source = SENDER
            )
            message = "An e-mail to an admissions officer has been sent. The response from the officer will be sent to " + user_email + "."

        except Exception as e:
            message = "Whoops, something went wrong. I couldn't send the email"
            print(e)
        finally:
            return close (session_attributes, 'Fulfilled', {'contentType': 'PlainText', 'content': message})
    else:
        return close (session_attributes, 'Fulfilled', {'contentType': 'PlainText', 'content': 'here'})


def write_to_intent_table(intent_request):
    try:
        dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
        table = dynamodb.Table('Intent-Utterances-v3')
        eastern = dateutil.tz.gettz('US/Eastern')
        tString = datetime.datetime.now(tz=eastern).strftime("%Y-%m-%d-%H-%M-%S")
        week = datetime.datetime.today() + datetime.timedelta(days=1)
        expiryDateTime = int(time.mktime(week.timetuple()))
        ttl = expiryDateTime
        
        tableInput = table.put_item(
            Item = {
                'ChatToken': intent_request['sessionAttributes']['GBCToken'],
                'TimeSent': tString,
                'Intent': intent_request['sessionAttributes']['currentIntent'],
                'Utterance': intent_request['sessionAttributes']['currentUtterance'],
                'ttl': ttl 
            })
        return 
    except Exception as e:
        print(e)


def elicit_email_information(intent_request, message):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    session_attributes['switched'] = 'false'
    send_message = intent_request['currentIntent']['slots']['sendMessage']
    send_email_slot= intent_request['currentIntent']['slots']['sendEmail']

    if send_email_slot == 'no':
        session_attributes['switched'] = 'false'
        return close(session_attributes, 'Fulfilled', {'contentType': 'PlainText', 'content': 'Okay, you can still ask me more questions! '})
    elif intent_request['currentIntent']['slots']['emailMemo']:
        intent_request['currentIntent']['slots']['emailMemo'] = session_attributes['placeholder']
        return send_email(intent_request)

    if not send_email_slot:
        return {
            'sessionAttributes': session_attributes,
            'dialogAction': {
                'type': 'ElicitSlot',
                'intentName': intent_request['currentIntent']['name'],
                'slots': intent_request['currentIntent']['slots'],
                'slotToElicit': 'sendEmail',
                'message': {
                    'contentType': 'PlainText',
                    'content': message + 'I can e-mail someone to help you. Would you like to e-mail an admissions officer?'
                },
                'responseCard': {
                    'version': 1,
                    'contentType': 'application/vnd.amazonaws.card.generic',
                    'genericAttachments': [
                        {
                            'buttons': [
                                {
                                    'text': 'Yes, please e-mail someone',
                                    'value': 'yes'
                                },
                                {
                                    'text': 'No, I don\'t need to',
                                    'value': 'no'
                                }
                            ]
                        }
                    ]
                }
            }
        }
        
    if not send_message:
        first_bubble = get_first_bubble_to_be_returned(session_attributes)
        return {
            'sessionAttributes': session_attributes,
            'dialogAction': {
                'type': 'ElicitSlot',
                'intentName': intent_request['currentIntent']['name'],
                'slots': intent_request['currentIntent']['slots'],
                'slotToElicit': 'sendMessage',
                'message': {
                    'contentType': 'PlainText',
                    'content': first_bubble
                },
                'responseCard': {
                    'version': 1,
                    'contentType': 'application/vnd.amazonaws.card.generic',
                    'genericAttachments': [
                        {
                            'buttons': [
                                {
                                    'text': 'Yes, talk directly to an officer. ',
                                    'value': 'emailing directly to an officer'
                                },
                                {
                                    'text': 'No, I don\'t need to. ',
                                    'value': 'no'
                                }
                            ]
                        }
                    ]
                }
                    
            }
        }
    
    if send_message == 'yes':
        message = "You can send an e-mail to: Julie.Lee2@georgebrown.ca. Please specify your country of origin and student ID in the email (if applicable)."
        return close (session_attributes, 'Fulfilled', {'contentType': 'PlainText', 'content': message})
    else:
        return send_email(intent_request)


def fulfilled_code_hook_handler(intent_request, data):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    intent_name = intent_request['currentIntent']['name']
    write_to_intent_table(intent_request)
    if intent_name == 'break':
        message = 'I\'m sorry that I couldn\'t help you today.'
    elif intent_name == 'insult':
        message = 'You seem to be getting frustrated. '
    elif intent_name == 'errorHandling':
        message = 'I can\'t seem to process your request. '
    else:
        message = "I\'m sorry that I couldn\'t help you today. "
    
    if(session_attributes['switched'] == 'true'):
        session_attributes['confirmed'] = 'no'
        if(data == 'yes'):
            return elicit_email_information(intent_request, message)
        else:
            session_attributes['lastIntent'] = 'defaultLI'
            session_attributes['currentUtterance'] = 'defaultCU'
            session_attributes['intentBeforeTheLastIntent'] = 'defaultIBLI'
            session_attributes['lastUtterance'] = 'defaultLU'
            session_attributes['currentIntent'] = 'defaultCI'
            session_attributes['switched'] = 'false'
            return {
                'sessionAttributes': session_attributes,
                'dialogAction': {
                    'type': 'ElicitIntent',
                    'message': {
                        'contentType': 'PlainText',
                        'content': 'Sorry about that, I\'ll reroute you to the correct answer'
                    }, 
                    'responseCard': {
                        'version': 1,
                        'contentType': 'application/vnd.amazonaws.card.generic',
                        'genericAttachments': [
                            {
                                'buttons': [
                                    {
                                        'text': 'Continue',
                                        'value': str(intent_request['currentIntent']['slots']['whatUserAsked'])
                                    }
                                ]
                            }
                        ]
                    }
                }
            }
    return elicit_email_information(intent_request, message)       
    
    
def dialog_code_hook_handler(intent_request):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    if (session_attributes['currentIntent'] == session_attributes['lastIntent'] and session_attributes['currentIntent'] == session_attributes['intentBeforeTheLastIntent']) or (session_attributes['currentUtterance'] == session_attributes['lastUtterance'] ):
        if(intent_request['currentIntent']['name'] == 'break' and session_attributes['switched'] == 'false') or (session_attributes['confirmed'] == 'yes'):
            return delegate(session_attributes, intent_request['currentIntent']['slots'])
        else:
            session_attributes['switched'] = 'true'
            intent_request['currentIntent']['slots']['whatUserAsked'] = session_attributes['currentUtterance']
            session_attributes['lastIntent'] = 'defaultLI'
            session_attributes['currentUtterance'] = 'defaultCU'
            session_attributes['intentBeforeTheLastIntent'] = 'defaultIBLI'
            session_attributes['lastUtterance'] = 'defaultLU'
            session_attributes['confirmed'] = 'yes'
            return {
                'sessionAttributes': session_attributes,
                'dialogAction': {
                    'type': 'ConfirmIntent',
                    'intentName': 'repeat',
                    'slots': {'placeholderSlot': None, 'whatUserAsked': intent_request['currentIntent']['slots']['whatUserAsked']},
                    'message': {
                        'contentType': 'PlainText',
                        'content': 'You keep asking me the same thing. Do you need any assistance?'
                    }, 
                    'responseCard': {
                        'version': 1,
                        'contentType': 'application/vnd.amazonaws.card.generic',
                        'genericAttachments': [
                            {
                                'buttons': [
                                    {
                                        'text': 'Yes, help me',
                                        'value': 'yes'
                                    },
                                    {
                                        'text': 'No, I meant what I said',
                                        'value': 'no'
                                    }
                                ]
                            }
                        ]
                    }
                }
            }
    else:
        print(intent_request['currentIntent']['name'])
        
    return delegate(session_attributes, intent_request['currentIntent']['slots'])


# --- main handler ---
def update_token(event):
    session_attributes = event['sessionAttributes'] if event['sessionAttributes'] is not None else {}
    session_attributes['lexChatToken'] = event['userId']
    dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
    table = dynamodb.Table('AskGeorgeTranscriptV6')
    eastern = dateutil.tz.gettz('US/Eastern')
    tString = datetime.datetime.now(tz=eastern).strftime("%Y-%m-%d-%H-%M-%S")
    
    if 'inputTranscript' in event: 
        inputData = event['inputTranscript']
    else:
        inputData = 'unknown utterance'
    
    if session_attributes['GBCuserId']:
        userId = session_attributes['GBCuserId']
    else:
        userId = 'unknown user'
    
    token = session_attributes['lexChatToken']
    dialog = 'user'
    order = session_attributes['order']
    session_attributes['order'] = str(int(order, 10) + 1)
    
    week = datetime.datetime.today() + datetime.timedelta(days=1)
    expiryDateTime = int(time.mktime(week.timetuple()))
    ttl = expiryDateTime
    orderString = order
    order = int(order, 10)
    current = datetime.datetime.today()
    t = int(time.mktime(current.timetuple()))
    
    if(inputData != "unknown utterance"):
       #output to table
        tableInput = table.put_item(
            Item = {
                'UserIdPrimaryKey': token, 
                'OrderBubble': order,
                'Dialog': dialog,
                'ChatMessage': inputData,
                'TimeSent': tString, 
                'TimeSentNumeric': t,
                'ttl': ttl,
                'GBCuserId': userId,
                'orderString': orderString
            })
    return
    
    
def lambda_handler(event, context):
    session_attributes = event['sessionAttributes'] if event['sessionAttributes'] is not None else {}

    if(session_attributes['switched'] == 'false'):
        event['sessionAttributes']['currentIntent'] = event['currentIntent']['name']
        
    if(event['invocationSource'] == 'DialogCodeHook'):
        return dialog_code_hook_handler(event)
    elif (event['invocationSource'] == 'FulfillmentCodeHook'):
        data = event['inputTranscript']
        return fulfilled_code_hook_handler(event, data)
    else:
        return close(event['sessionAttributes'], 'Fulfilled', 'I don\'t understand')