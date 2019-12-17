import json
import array
import datetime
import time
import ast
import os
import dateutil.parser
import logging
from botocore.vendored import requests
import boto3
import base64
import re
from boto3.dynamodb.conditions import Key, Attr
from botocore.exceptions import ClientError
from datetime import datetime, timedelta


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
            # Deal with the excep tion here, and/or rethrow at your discretion.
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


def upload_intent_info(event, context):
    secrets_resp = get_secret()
    secrets_data = json.loads(secrets_resp)
    username = secrets_data['username']
    password = secrets_data['password']
    counter = 0
    i = 0

    dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
    table = dynamodb.Table('Intent-Utterances-v3')
    response = table.scan( ProjectionExpression = "ChatToken, TimeSent, Intent, Utterance" )
    numberOfIntent = response['Count']

    while(i < numberOfIntent):
        individual_transcript = json.dumps(response['Items'][i])
        time_sent_check = json.loads(individual_transcript)
        time_obj = datetime.strptime(time_sent_check['TimeSent'], '%Y-%m-%d-%H-%M-%S')
        now = datetime.now()
        past = time_obj + timedelta(days=1)

        if(past > now):
            transcript_info = individual_transcript
            transcript_out = ""
            link = "false"

            for x in transcript_info:
                if x == ">":
                    link = "false"
                elif x == "<" or link == "true":
                    link = "true"
                elif x == " ":
                    transcript_out = transcript_out + '%20'
                elif x == "}":
                    transcript_out = transcript_out +'%7D'
                elif x == "{":
                    transcript_out = transcript_out + '%7B'
                elif x == "\\":
                    transcript_out = transcript_out 
                else: 
                    transcript_out = transcript_out + x;

            transcript_send = transcript_out.replace("&nbsp", "") 
            transcript_send_2 = transcript_send.replace("errorHandling", "unrecognized")
            url = 'https://dmzmsa01.georgebrown.ca/ChatbotService/api/updateIntentUtterances?username=' + username + '&password=' + password + '&data=' + transcript_send_2
            info = requests.get(url)
            data = info.json()

            if(data['addedRecords'] > 0):
                counter = counter + data['addedRecords']
        i = i+1

    return counter

    
def upload_transcript(event,context):
    secrets_resp = get_secret()
    secrets_data = json.loads(secrets_resp)
    username = secrets_data['username']
    password = secrets_data['password']
    counter = 0
    i = 0

    dynamodb = boto3.resource('dynamodb', region_name='us-east-1')
    table = dynamodb.Table('AskGeorgeTranscriptV6')
    response = table.scan( ProjectionExpression= "Dialog, ChatMessage, GBCuserId, TimeSent, UserIdPrimaryKey, orderString" )
    numberOfTranscript = response['Count']

    while(i < numberOfTranscript):
        individual_transcript = json.dumps(response['Items'][i])
        time_sent_check = json.loads(individual_transcript)
        time_obj = datetime.strptime(time_sent_check['TimeSent'], '%Y-%m-%d-%H-%M-%S')

        now = datetime.now()
        past = time_obj + timedelta(days=1)

        if(past > now):
            transcript_info = individual_transcript
            transcript_out = ""
            link = "false"
            transcript_info= re.sub('\r\n', '', transcript_info)

            for x in transcript_info:
                if x == ">":
                    link = "false"
                elif x == "<" or link == "true":
                    link = "true"
                elif x == " ":
                    transcript_out = transcript_out + '%20'
                elif x == "}":
                    transcript_out = transcript_out + '%7D'
                elif x == "{":
                    transcript_out = transcript_out + '%7B'
                elif x == "\\":
                    transcript_out = transcript_out
                else: 
                    transcript_out = transcript_out + x;

            transcript_send = transcript_out.replace("&nbsp;", "") 
            transcript_send_2 = transcript_send.replace("bubble%20after%20\"", "bubble%20after%20[")
            transcript_send_3 = transcript_send_2.replace("\"%20will%20be%20e-mailed", "]%20will%20be%20e-mailed")
            url = 'https://dmzmsa01.georgebrown.ca/ChatbotService/api/updateTranscript?username=' + username + '&password=' + password +'&data=' + transcript_send_3
            info = requests.get(url)
            data = info.json()

            if(data['addedRecords'] > 0):
                counter = counter + data['addedRecords']
        i = i+1

    return counter


def lambda_handler(event, context):
    countTranscripts = upload_transcript(event, context)
    countIntent = upload_intent_info(event,context)
