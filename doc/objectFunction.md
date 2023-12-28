# 对象操作文档


# 新增
```

{
    "method": "serverless.function.runtime.invoke",
    "params": "{\"functionTarget\":\"cloud-object-demo\",\"functionArgs\":{\"method\":\"add\",\"params\":[{\"name\":\"DCloud\",\"subType\":\"uniCloud\",\"createTime\":1703606590815}],\"clientInfo\":{\"PLATFORM\":\"h5\",\"OS\":\"windows\",\"APPID\":\"__UNI__5C6D4E4\",\"DEVICEID\":\"17036060196966950877\",\"CLIENT_SDK_VERSION\":\"1.0.23\",\"LOCALE\":\"zh-Hans\"}}}",
    "spaceId": "1a10688c-0b30-4aaa-bbc1-7f4948cc562a",
    "timestamp": 1703606590816,
    "token": "55fdb68f-ffd0-4af8-83f2-d13b7ab84beb"
}


-----


{
    "success": true,
    "data": {
        "id": "658af940e0ec190274cea05c"
    },
    "header": {
        "x-serverless-cold-boot": "true",
        "x-serverless-request-id": "ac1cc2f81703606590986146065",
        "x-serverless-runtime-version": "2.0.6",
        "x-fc-code-checksum": "4398124040086416443",
        "access-control-expose-headers": "Date,x-fc-request-id,x-fc-error-type,x-fc-code-checksum,x-fc-invocation-duration,x-fc-max-memory-usage,x-fc-log-result,x-fc-invocation-code-version,x-fc-instance-id",
        "x-fc-invocation-service-version": "LATEST",
        "x-fc-instance-id": "c-658af93f-189b68fc-1076dfe07057",
        "content-length": "214",
        "date": "Tue, 26 Dec 2023 16:03:12 GMT",
        "x-fc-request-id": "1-658af93f-a6c876063a36f1d872097e6a",
        "x-fc-max-memory-usage": "31.12",
        "x-fc-invocation-duration": "76",
        "content-type": "application/octet-stream",
        "x-serverless-http-status": "200"
    }
}

```


#  删除
```
{
    "method": "serverless.function.runtime.invoke",
    "params": "{\"functionTarget\":\"cloud-object-demo\",\"functionArgs\":{\"method\":\"remove\",\"params\":[],\"clientInfo\":{\"PLATFORM\":\"h5\",\"OS\":\"windows\",\"APPID\":\"__UNI__5C6D4E4\",\"DEVICEID\":\"17036060196966950877\",\"CLIENT_SDK_VERSION\":\"1.0.23\",\"LOCALE\":\"zh-Hans\"}}}",
    "spaceId": "1a10688c-0b30-4aaa-bbc1-7f4948cc562a",
    "timestamp": 1703606703208,
    "token": "55fdb68f-ffd0-4af8-83f2-d13b7ab84beb"
}


-----


{
    "success": true,
    "data": {
        "status": 0,
        "msg": "成功删除unicloud-test内第一条数据"
    },
    "header": {
        "x-serverless-request-id": "ac1cc3061703606703208166017",
        "x-serverless-runtime-version": "2.0.6",
        "x-fc-code-checksum": "4398124040086416443",
        "access-control-expose-headers": "Date,x-fc-request-id,x-fc-error-type,x-fc-code-checksum,x-fc-invocation-duration,x-fc-max-memory-usage,x-fc-log-result,x-fc-invocation-code-version,x-fc-instance-id",
        "x-fc-invocation-service-version": "LATEST",
        "x-fc-instance-id": "c-658af93f-189b68fc-1076dfe07057",
        "content-length": "213",
        "date": "Tue, 26 Dec 2023 16:05:03 GMT",
        "x-fc-request-id": "1-658af9af-edffdf61c6ed60368b128615",
        "x-fc-max-memory-usage": "31.67",
        "x-fc-invocation-duration": "81",
        "content-type": "application/octet-stream",
        "x-serverless-http-status": "200"
    }
}



```


# 修改



{
    "method": "serverless.function.runtime.invoke",
    "params": "{\"functionTarget\":\"cloud-object-demo\",\"functionArgs\":{\"method\":\"update\",\"params\":[{\"name\":\"DCloud\",\"subType\":\"html 5+\",\"createTime\":1703606755640}],\"clientInfo\":{\"PLATFORM\":\"h5\",\"OS\":\"windows\",\"APPID\":\"__UNI__5C6D4E4\",\"DEVICEID\":\"17036060196966950877\",\"CLIENT_SDK_VERSION\":\"1.0.23\",\"LOCALE\":\"zh-Hans\"}}}",
    "spaceId": "1a10688c-0b30-4aaa-bbc1-7f4948cc562a",
    "timestamp": 1703606755640,
    "token": "55fdb68f-ffd0-4af8-83f2-d13b7ab84beb"
}
-----


{
    "success": true,
    "data": {
        "status": 0,
        "msg": "集合第一条数据由{\"_id\":\"63355192f808ea0001cbf758\",\"name\":\"DCloud\",\"subType\":\"uniCloud\",\"createTime\":1664438673706}修改为{\"_id\":\"63355192f808ea0001cbf758\",\"name\":\"DCloud\",\"subType\":\"html 5+\",\"createTime\":1703606755640}"
    },
    "header": {
        "x-serverless-request-id": "ac1cc3061703606755644170741",
        "x-serverless-runtime-version": "2.0.6",
        "x-fc-code-checksum": "4398124040086416443",
        "access-control-expose-headers": "Date,x-fc-request-id,x-fc-error-type,x-fc-code-checksum,x-fc-invocation-duration,x-fc-max-memory-usage,x-fc-log-result,x-fc-invocation-code-version,x-fc-instance-id",
        "x-fc-invocation-service-version": "LATEST",
        "x-fc-instance-id": "c-658af93f-189b68fc-1076dfe07057",
        "content-length": "426",
        "date": "Tue, 26 Dec 2023 16:05:55 GMT",
        "x-fc-request-id": "1-658af9e3-eea452214f5cc9d0a84c6c4b",
        "x-fc-max-memory-usage": "32.13",
        "x-fc-invocation-duration": "95",
        "content-type": "application/octet-stream",
        "x-serverless-http-status": "200"
    }
}





# 分页查询
```

{
    "method": "serverless.function.runtime.invoke",
    "params": "{\"functionTarget\":\"cloud-object-demo\",\"functionArgs\":{\"method\":\"get\",\"params\":[],\"clientInfo\":{\"PLATFORM\":\"h5\",\"OS\":\"windows\",\"APPID\":\"__UNI__5C6D4E4\",\"DEVICEID\":\"17036060196966950877\",\"CLIENT_SDK_VERSION\":\"1.0.23\",\"LOCALE\":\"zh-Hans\"}}}",
    "spaceId": "1a10688c-0b30-4aaa-bbc1-7f4948cc562a",
    "timestamp": 1703606757647,
    "token": "55fdb68f-ffd0-4af8-83f2-d13b7ab84beb"
}

---

{
    "success": true,
    "data": {
        "affectedDocs": 10,
        "data": [
            {
                "_id": "63355192f808ea0001cbf758",
                "name": "DCloud",
                "subType": "html 5+",
                "createTime": 1703606755640
            },
            {
                "_id": "63355b5e702e9d000176cd04",
                "name": "DCloud",
                "subType": "uniCloud",
                "createTime": 1664441172890
            },
            {
                "_id": "63355c5af74ea000015e797a",
                "name": "DCloud",
                "subType": "uniCloud",
                "createTime": 1664441433825
            },
            {
                "_id": "63357705398d8500016dfc7e",
                "name": "DCloud",
                "subType": "uniCloud",
                "createTime": 1664448259848
            },
            {
                "_id": "63357df4702e9d00017aeec9",
                "name": "DCloud",
                "subType": "uniCloud",
                "createTime": 1664450027701
            },
            {
                "_id": "633587f190a11e00017978b5",
                "name": "DCloud",
                "subType": "uniCloud",
                "createTime": 1664452594595
            },
            {
                "_id": "6335b5ea60cd3e00012fa83d",
                "name": "DCloud",
                "subType": "uniCloud",
                "createTime": 1664464360510
            },
            {
                "_id": "6335bc14d577fa000194ed50",
                "name": "DCloud",
                "subType": "uniCloud",
                "createTime": 1664465939254
            },
            {
                "_id": "6335c244fcf4a30001a99993",
                "name": "DCloud",
                "subType": "uniCloud",
                "createTime": 1664467523421
            },
            {
                "_id": "6335c988fcf4a30001aa82d3",
                "name": "DCloud",
                "subType": "uniCloud",
                "createTime": 1664469382862
            }
        ]
    },
    "header": {
        "x-serverless-request-id": "ac1cd3651703606757623154984",
        "x-serverless-runtime-version": "2.0.6",
        "x-fc-code-checksum": "4398124040086416443",
        "access-control-expose-headers": "Date,x-fc-request-id,x-fc-error-type,x-fc-code-checksum,x-fc-invocation-duration,x-fc-max-memory-usage,x-fc-log-result,x-fc-invocation-code-version,x-fc-instance-id",
        "x-fc-invocation-service-version": "LATEST",
        "x-fc-instance-id": "c-658af93f-189b68fc-1076dfe07057",
        "content-length": "1166",
        "date": "Tue, 26 Dec 2023 16:05:57 GMT",
        "x-fc-request-id": "1-658af9e5-5a144fc22b005eea47f3c36e",
        "x-fc-max-memory-usage": "32.51",
        "x-fc-invocation-duration": "59",
        "content-type": "application/octet-stream",
        "x-serverless-http-status": "200"
    }
}

```