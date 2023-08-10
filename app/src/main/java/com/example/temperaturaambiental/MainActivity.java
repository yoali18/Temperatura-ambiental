package com.example.temperaturaambiental;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity  extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private TextView txtTemperature;

    @Override
    protected void onCreate(Bundle instancia) {
        super.onCreate(instancia);
        setContentView(R.layout.activity_main);

         txtTemperature = findViewById(R.id.txtTemperature);
         sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
         temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.BODY_SENSORS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.BODY_SENSORS},
                    1);
        }
        else{
            startTemperatureAmbientMonitoring();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopTemperatureAmbientMonitoring();
    }

    //Iniciar la escucha de los datos del sensor
    private void startTemperatureAmbientMonitoring()
    {
        sensorManager.registerListener(this,temperatureSensor
                ,SensorManager.SENSOR_DELAY_NORMAL);
    }

    //Detener la escucha del sensor
    private void stopTemperatureAmbientMonitoring()
    {
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            txtTemperature.setText("Temperatura: " + event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
