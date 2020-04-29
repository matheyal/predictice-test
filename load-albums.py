#!/usr/bin/python

import json
import requests

# Load albums file
with open("albums_sample.json", "r") as f:
    albums = json.load(f)

print("About to insert %s albums into ES" % len(albums))

payload = []
for album in albums:
    metdata = {
        "index": {
            "_index": "album",
            "_id": album["id"]
        }
    }
    payload.append(json.dumps(metdata))
    payload.append(json.dumps(album))

url = "http://localhost:9200/_bulk?pretty"
r = requests.post(
    url, 
    data="\n".join(payload) + "\n",
    headers={ "Content-Type": "application/json" }
)
r.raise_for_status()

print("Successfully saved albums into ES")