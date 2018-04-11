package com.example.ioanavaida.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class ArduinoActivity extends AppCompatActivity {

    private static final int handlerState = 0;
    // Connection variables
    // TODO - set the name and adress of the arduino device
    private final String ARDUINO_NAME = "SETTHENAME";
    private final String ARDUINO_ADDRESS = "GETTHEADDRESS";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
    boolean deviceConnected = false;
    boolean stopThread;
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private TextView txtString;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Button buttonStart, buttonStop;
    private TextView textView;
    private Handler blueToothInHandler;
    private StringBuilder receivedData = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arduino);
        textView = (TextView) findViewById(R.id.pulseView);
        txtString = (TextView) findViewById(R.id.txtString);
        buttonStart = (Button) findViewById(R.id.btnStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the start method is clicked on.);
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_LONG);
                toast.show();
                onClickStart();
            }
        });
        buttonStop = (Button) findViewById(R.id.btnStop);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the start method is clicked on.);
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Connecting...", Toast.LENGTH_LONG);
                toast.show();
                try {
                    onClickStop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public boolean BlueToothInit() {
        boolean found = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Device doesnt Support Bluetooth", Toast.LENGTH_SHORT).show();
        }
        if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (bluetoothAdapter != null && bluetoothAdapter.isEnabled()) {
            Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
            if (bondedDevices.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please Pair the Device first", Toast.LENGTH_SHORT).show();
            } else {
                for (BluetoothDevice iterator : bondedDevices) {
                    if (iterator.getAddress().equals(ARDUINO_ADDRESS)) {
                        device = iterator;
                        found = true;
                        break;
                    }
                }
            }
        }
        return found;
    }

    public boolean BlueToothConnect() {
        boolean connected = true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
        if (connected) {
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connected;
    }

    public void onClickStart() {
        if (BlueToothInit()) {
            if (BlueToothConnect()) {
                deviceConnected = true;
                beginListenForData();
                textView.append("\nConnection Opened!\n");
            }
        }
    }

    void beginListenForData() {
        stopThread = false;
        Thread thread = new Thread(sRunnable);
        blueToothInHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == handlerState) {
                    String readMessage = (String) msg.obj;
                    receivedData.append(readMessage);
                    int endOfLineIndex = receivedData.indexOf("~");
                    if (endOfLineIndex > 0) {
                        String data = receivedData.substring(0, endOfLineIndex);
                        setDataInUi(data);
                        receivedData.delete(0, receivedData.length());
                    }
                }
            }
        };
        thread.start();
    }

    public void onClickStop() throws IOException {
        stopThread = true;
        outputStream.close();
        inputStream.close();
        socket.close();
        deviceConnected = false;
        textView.append("\nConnection Closed!\n");
    }

    public void setDataInUi(String data) {
        txtString.setText(String.format("Data Received = %s", data));
    }
    private final Runnable sRunnable = new Runnable() {
        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            // Keep looping until the connection is closed
            while (stopThread = false) {
                try {
                    //read bytes from input buffer
                    bytes = inputStream.read(buffer);
                    String readMessage = new String(buffer, 0, bytes);
                    blueToothInHandler.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    stopThread = true;
                }
            }
        }
    };
}