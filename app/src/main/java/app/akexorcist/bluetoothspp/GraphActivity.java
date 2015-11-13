package app.akexorcist.bluetoothspp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphActivity extends Activity {
    CustomGraph graphTemperatura;
    CustomGraph graphOxigeno;//    ArrayList<Object[]> graphData;
    ArrayList<GraphDataPackage> graphData;
    LineGraphSeries<DataPoint> seriesSensor1 = new LineGraphSeries<>( new DataPoint[]{} );
    LineGraphSeries<DataPoint> seriesSensor2 = new LineGraphSeries<>( new DataPoint[]{} );

    final static private String TAG = "app.akexorcist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Log.i(TAG, "on Graph Activity");

        Bundle TerminalData = getIntent().getExtras();
        if (TerminalData == null){
            return;
        }
        // TODO
//        graphData = (ArrayList<Object[]>) TerminalData.get("graphData");
        graphData = (ArrayList<GraphDataPackage>) TerminalData.get("graphSomethin");

        makeDayGraphSensor1();
        makeDayGraphSensor2();
        setDayData();
    }

    private void makePrueba(){
        // generate Dates
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 5);
        calendar.set(Calendar.MINUTE, 30);
        Date d1 = calendar.getTime();
        calendar.add(Calendar.HOUR, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.HOUR, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.HOUR, 1);
        Date d4 = calendar.getTime();

        Log.i(TAG, " TIME OF DATE = " + d1.getTime());

        GraphView graph = (GraphView) findViewById(R.id.graphSensor1);

        // you can directly pass Date objects to DataPoint-Constructor
        // this will convert the Date to double via Date#getTime()
//        seriesSensor1 = new LineGraphSeries<DataPoint>(new DataPoint[] {
//                new DataPoint(d1, 1),
//                new DataPoint(d2, 5),
//                new DataPoint(d3, 3),
//                new DataPoint(d4, 2)
//        });
        graph.addSeries(seriesSensor1);

        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this, df));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d4.getTime());
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setScalable(true);
    }

    private void makeDayGraphSensor1(){
        graphTemperatura = new CustomGraph( (GraphView) findViewById(R.id.graphSensor1) );
        graphTemperatura.setup(getString(R.string.TituloGraphSensor1), getString(R.string.GraphEjeHorozontal));

        GraphDataPackage firstElement = graphData.get(0);
        GraphDataPackage lastElement = graphData.get(graphData.size() - 1);
        graphTemperatura.set_X_Axis(convertToDay(firstElement.date), convertToDay(lastElement.date));
        graphTemperatura.style();

        graphTemperatura.styleSeries(seriesSensor1, getString(R.string.TituloGraphSensor1), Color.BLUE);
        graphTemperatura.addSeries(seriesSensor1);

        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
        graphTemperatura.setFormatter(new DateAsXAxisLabelFormatter(this, df));

        NotificacionPeligroTemperatura();
    }

    private void makeDayGraphSensor2(){
        graphOxigeno = new CustomGraph( (GraphView) findViewById(R.id.graphSensor2) );
        graphOxigeno.setup(getString(R.string.TituloGraphSensor2), getString(R.string.GraphEjeHorozontal));
        graphOxigeno.set_Y_axis();

        GraphDataPackage firstElement2 = graphData.get(0);
        GraphDataPackage lastElement2 = graphData.get(graphData.size() - 1);
        graphOxigeno.set_X_Axis(convertToDay(firstElement2.date), convertToDay(lastElement2.date));
        graphOxigeno.style();

        graphOxigeno.styleSeries(seriesSensor2, getString(R.string.TituloGraphSensor2), Color.RED);
        graphOxigeno.addSeries(seriesSensor2);

        DateFormat df = DateFormat.getTimeInstance(DateFormat.SHORT);
        graphOxigeno.setFormatter(new DateAsXAxisLabelFormatter(this, df));

        NotificacionPeligroOxigeno();
    }

    void setDayData() {
        for (GraphDataPackage line : graphData){
            //TODO
            Date hourOfDay = convertToDay(line.date);
            seriesSensor1.appendData(new DataPoint( hourOfDay , line.sensor1), true, 9999 );
            seriesSensor2.appendData(new DataPoint( hourOfDay , line.sensor2), true, 9999 );
        }
    }

    private Date convertToDay(String date){
        // DateAndHour = [ "15/10/2015" , "14:25"  ]
        String[] DateAndHour  = date.split("-");
        String[] HourAndMinute = DateAndHour[1].split(":");
        String hour = HourAndMinute[0];
        String minute = HourAndMinute[1];

        //String formatMinute = String.format("%02d", Integer.valueOf(minute));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
        calendar.set(Calendar.MINUTE, Integer.valueOf(minute) );

        return calendar.getTime();
    }


    private void NotificacionPeligroTemperatura(){
        int ultimoDato = graphData.get(graphData.size() - 1).sensor1;
        if (ultimoDato <= 12 ){
            // letal
            Toast.makeText(getApplicationContext(), " letal: temperatura en 12\u2103",
                    Toast.LENGTH_LONG).show();
        } else if ( ultimoDato <= 15){
            // critico
            Toast.makeText(getApplicationContext(),  " critico: temperatura en 15\u2103",
                    Toast.LENGTH_LONG).show();
        } else if (ultimoDato <=  25){
            // alerta
            Toast.makeText(getApplicationContext(), " alerta: temperatura por debajo de 25\u2103",
                    Toast.LENGTH_LONG).show();
        } else if (32 <= ultimoDato  ){
            // alerta
            Toast.makeText(getApplicationContext(), " alerta: temperatura alta",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void NotificacionPeligroOxigeno(){
        int ultimoDato = graphData.get(graphData.size() - 1).sensor2;
        if (ultimoDato <= 5 ){
            // letal
            Toast.makeText(getApplicationContext(), " alerta: nivel de oxigeno bajo", Toast.LENGTH_LONG).show();
        }
    }
}





