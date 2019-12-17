from __future__ import print_function
import boto3
import datetime
import time
import os
from botocore.exceptions import ClientError
import dateutil.parser
import logging
from botocore.vendored import requests
import botocore.exceptions
import hmac
import hashlib
import base64
import json
import uuid


# gets secret from AWS Secrets Manager
def get_secret():
    secret_name = "TestChatbot"
    region_name = "us-east-1"

    session = boto3.session.Session()
    client = session.client(
        service_name='secretsmanager',
        region_name=region_name
    )

    try:
        get_secret_value_response = client.get_secret_value(SecretId=secret_name)
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


def get_role_login(intent_request):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    secrets_resp = get_secret()
    secrets_data = json.loads(secrets_resp)
    username = secrets_data['username']
    password = secrets_data['password']
    
    if(session_attributes['GBCuserId']):
        userId = session_attributes['GBCuserId']
        url = 'https://dmzmsa01.georgebrown.ca/ChatbotService/api/getRole?username=' + username + '&password=' + password + '&userId=' + userId
        response = requests.get(url)
        data = response.json()
    
        if(data['success'] == True):
            session_attributes['authRole'] = str(data['authRole'])
    return


# checks session attributes to see if we already have saved the user information
def check_session_attributes(intent_request):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    authUser = 'false'
    login = 'false'
    
    if 'authUser' in session_attributes:
        authUser = session_attributes['authUser']
    elif login == "true":
        finish_login(intent_request)
        if('authUser' in session_attributes):
            authUser = session_attributes['authUser']
    elif 'GBCToken' not in session_attributes:
        return set_up_login(intent_request)
    elif session_attributes['GBCToken'] == 'testtoken':
        return set_up_login(intent_request)
    else:
        get_auth_user_service(intent_request)
        authUser = session_attributes['authUser']
    
    # send auth verdict
    if authUser == "true":
        authItem = "true"
    else:
        authItem = "false"
    return authItem


def set_up_login(intent_request):
    intent_request['sessionAttributes']['lexChatToken'] = intent_request['userId']
    return close(intent_request['sessionAttributes'], 'Fulfilled', {
            'contentType': 'PlainText',
            'content': 'logging in...'
        } )
    

# finish log-in process
def finish_login(intent_request):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    url = 'https://dmzgrg01u.georgebrown.ca/gbcauth/userInfo/findByLexToken?lexChatToken=' + intent_request['userId']
    response = requests.get(url)
    data = response.json()
    
    if('class' in data):
        session_attributes['authUser'] = 'true'
        if(data['oasUserId']):
            session_attributes['GBCuserId'] = data['oasUserId']
            get_role_login(intent_request)
        
        if(data['oasAppId']):
            session_attributes['GBCappId'] = data['oasAppId']
            get_role_login(intent_request)
            session_attributes['authApp'] = 'true'
        
        if(data['bannerId']):
            session_attributes['bannerId'] = data['bannerId']
    else:
        session_attributes['authUser'] = "error"
    return
    

def get_auth_user_service(intent_request):
    # call web service to authenticate the user application and store in session attributes
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    secrets_resp = get_secret()
    secrets_data = json.loads(secrets_resp)
    username = secrets_data['username']
    password = secrets_data['password']
    userId = session_attributes['GBCuserId']
    token = session_attributes['GBCToken']

    url = 'https://dmzmsa01.georgebrown.ca/ChatbotService/api/getAuthUser?username=' + username + '&password=' + password + '&token=' + token + '&userId=' + userId 
    response = requests.get(url)
    data = response.json()
    
    if(data['success'] == False):
        session_attributes['authUser'] = "error"
    else:
        session_attributes['authUser'] = data['authUser']

    return
    

# handler for authentication - return messages to bot as needed
def auth_session (intent_request):
    session_attributes = intent_request['sessionAttributes'] if intent_request['sessionAttributes'] is not None else {}
    authItem = check_session_attributes(intent_request)
    return authItem
