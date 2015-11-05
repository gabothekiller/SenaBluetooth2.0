package app.akexorcist.bluetoothspp;

import android.graphics.Color;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Clase para configurar por igual a todos los graficos que se van
 * a hacer en la aplicacion.
 */
public class CustomGraph{
    final static private int NUM_VERTICAL_LABELS = 10;
    final static private int NUM_HORIZONTAL_LABELS = 4;

    GraphView graph;

    public CustomGraph(GraphView graph){
        this.graph = graph;
    }

    public void setup ( String title, String horizontalLabel ){
        // graph.setTitle( title );
        graph.getViewport().setScalable(true);
        graph.getGridLabelRenderer().setHorizontalAxisTitle(horizontalLabel);
    }

    public void style(){
        // leyenda de la grafica
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setTextColor(Color.WHITE);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);

        graph.setTitleColor(Color.WHITE);

        graph.getGridLabelRenderer().setHorizontalAxisTitleColor(Color.WHITE);
        graph.getGridLabelRenderer().setHorizontalLabelsColor(Color.WHITE);
        graph.getGridLabelRenderer().setVerticalAxisTitleColor(Color.WHITE);
        graph.getGridLabelRenderer().setVerticalLabelsColor(Color.WHITE);

    }

    public void styleSeries( LineGraphSeries<DataPoint> series, String title, int color){
        series.setColor(color);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(4);
        series.setTitle( title );
    }

    public void set_X_Axis(int firstHour, int lastHour){
        graph.getGridLabelRenderer().setNumHorizontalLabels(NUM_HORIZONTAL_LABELS);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(firstHour);
        graph.getViewport().setMaxX(lastHour);
    }

    public void set_Y_axis(){
        graph.getGridLabelRenderer().setNumVerticalLabels( NUM_VERTICAL_LABELS );
        graph.getViewport().setYAxisBoundsManual(false);

        // maximas o minimas temperaturas
        //graph.getViewport().setMinY(0);
        //graph.getViewport().setMaxY(20);
    }

    public void addSeries(LineGraphSeries<DataPoint> series ){
        graph.addSeries(series);
    }

    public void setFormatter( DefaultLabelFormatter format ){
        graph.getGridLabelRenderer().setLabelFormatter( format  );
    }
}