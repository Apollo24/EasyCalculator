package com.antonsusman.easycalculator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private TextView Scr;
    private float NumberBf=0, NumAf, result=0;
    private String Operation, mod="replace";

    private ClipboardManager myClipboard;
    private ClipData myClip;

    int MENU_COLOR_RED = 1;
    int MENU_COLOR_GREEN = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Scr = (TextView) findViewById(R.id.txtScreen);
        Scr.setText("");

        registerForContextMenu(Scr);

        int idList[] = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnPlus, R.id.btnMinus, R.id.btnX, R.id.btnDiv,
                R.id.btnClear,R.id.btnEquals, R.id.btnDot, R.id.btnCopy };

        for(int id:idList) {
            View v = (View) findViewById(id);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClick(v);
                }
            });
        }


        Button copy=(Button)findViewById(R.id.btnCopy);
        final ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

       copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text;
                text = Scr.getText().toString();

                myClip = ClipData.newPlainText("text", text);
                myClipboard.setPrimaryClip(myClip);

                Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
       });

    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        int MENU_COLOR_RED = 1;
        int MENU_COLOR_GREEN = 2;
        int MENU_COLOR_BLUE = 3;
        menu.add(0, MENU_COLOR_RED, 0, "Red");
        menu.add(0, MENU_COLOR_GREEN, 0, "Green");
        menu.add(0, MENU_COLOR_BLUE, 0, "Blue");

    }

    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case 1:
                Scr.setTextColor(Color.RED);
                break;
            case 2:
                Scr.setTextColor(Color.GREEN);
                break;
            case 3:
                Scr.setTextColor(Color.BLUE);
                break;


        }
        return super.onContextItemSelected(item);
    }


    public void mMath(String str) {
        mResult();
        try {
            NumberBf = Float.parseFloat(Scr.getText().toString());
            Operation = str;
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), (CharSequence) e, Toast.LENGTH_SHORT).show();
            Scr.setText("SYNTAX ERROR");
            mod="replace";
        }
    }


    public void mResult() {
        NumAf = 0;
        if(!Scr.getText().toString().trim().isEmpty())
            NumAf = Float.parseFloat(Scr.getText().toString());
        result = NumAf;

        try {
            switch (Operation) {
                case "+":
                    result = NumAf + NumberBf;
                    break;
                case "-":
                    result = NumberBf - NumAf;
                    break;
                case "*":
                    result = NumAf * NumberBf;
                    break;
                case "/":
                    result = NumberBf / NumAf;
                    break;
                default:
                    result = NumAf;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Scr.setText(String.valueOf(result));
        mod = "replace";
    }
    public void getKeyboard(String str) {
        String ScrTxt = Scr.getText().toString();
        ScrTxt += str;
        if(mod.equals("add"))
            Scr.setText(ScrTxt);
        else
            Scr.setText(str);
        mod = "add";
    }
    public void onButtonClick(View v) {

        final Vibrator vibe = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);

        switch (v.getId()) {


            case R.id.btnClear: //Clear
                Scr.setText("");
                NumberBf = 0;
                Operation = "";
                vibe.vibrate(200);
                break;
            case R.id.btnPlus:
                mMath("+");
                break;
            case R.id.btnMinus:

                mMath("-");
                break;
            case R.id.btnX:
                mMath("*");
                break;
            case R.id.btnDiv:
                mMath("/");
                break;
            case R.id.btnEquals:
                mResult();
                Operation = "";
                NumberBf = 0;
                break;
            default:
                String numb = ((Button) v).getText().toString();
                getKeyboard(numb);
                break;
        }
    }
}


