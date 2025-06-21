The Most important information 

We need a device Token and the domain web 'thingsboard.cloud'

- Library 

    _# ThingsBoard.h_

- To do the connection 

    _tb.connect(thingsboardServer, TOKEN)_

- save the data in the array with the its size

    ``` 
    const int data_items = 1; 
    Telemetry data[data_items] = {
        { "key", value }
    };
    ```

- send data
    ```
    tb.sendTelemetry(data, data_items);
    ```

- communication with a widget _Led indicator_
    ```
    tb.sendAttributeBool("value1", false);  
    ```
Note: the _value1_ is the widget id, you found it in **configuration > advanced** of widget

Due: *RPC_CALLBACKS*