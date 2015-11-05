package app.akexorcist.bluetoothspp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Main extends Activity implements OnClickListener {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btnTerminal = (Button) findViewById(R.id.btnTerminal);
        btnTerminal.setOnClickListener(this);
    }

    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), TerminalActivity.class);
        startActivity(intent);
    }
}
