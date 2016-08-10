package pt.com.mkmt2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class CodeActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    private TextView tvTime;
    private TextView tvCodigo;
    private Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_g);
        toolbar.setLogo(R.drawable.logo);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        accessViews();
    }

    public void accessViews() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        tvTime= (TextView)findViewById(R.id.tv_time);
        tvCodigo=(TextView)findViewById(R.id.tv_codigo);
        progressBar.setMax(30);
        progressBar.setIndeterminate(false);

       t= new Thread(new Runnable() {
            int count=0;
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(0);
                        tvCodigo.setText(gerarPin());
                    }
                });
                while(count<30){
                    try {
                        count++;
                        progressBar.setProgress(count);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvTime.setText((31 - count) + " s");
                            }
                        });
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("30 s");
                        accessViews();
                    }
                });
            }
        });
        t.start();
    }

    @Override
    protected void onDestroy() {
        t.interrupt();
        super.onDestroy();
    }

    public String gerarPin(){
        Random ra= new Random();
        String num = ""+ra.nextInt(999999);
        Toast.makeText(this,num ,Toast.LENGTH_SHORT).show();

        if(num.length()<6){
            while(num.length()<6){
                num = ""+ra.nextInt(999999);
            }
        }
        return num;
    }
}
