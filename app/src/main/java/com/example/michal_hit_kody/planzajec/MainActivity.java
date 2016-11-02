package com.example.michal_hit_kody.planzajec;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.DateTimeKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    public Button zatwierdz;
    public ListView plan;
    public Spinner tryb;
    public EditText wartosc;

    private static final String SAMPLE_DB_NAME = "Plan";
    private static final String SAMPLE_TABLE_NAME = "Karta";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    //Deklaracja zmiennych dla pobieranych danych z bazy
    private String tab[]=new String[100];
    private String tab1[]=new String[100];
    private String tab2[]=new String[100];
    private String tab3[]=new String[100];
    private String tab4[]=new String[100];
    private String tab5[]=new String[100];


    String data,zmienna,zapytanie;
    int typ=0;
    Calendar myCalendar;
    private SimpleDateFormat sdf;
    DatePickerDialog.OnDateSetListener date;



    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }

    private void readsqlLight() {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Log.i("myTag",""+typ);
            switch (typ) {
                case 0:
                    zapytanie = "Select * from Plan_zajec where Data ='" + zmienna + "'";
                    break;
                case 2:
                    zapytanie = "Select * from Plan_zajec where Czas_s = '" + zmienna + "'";
                    break;
                case 3:
                    zapytanie = "Select * from Plan_zajec where Zajecia  like '%" + zmienna + "%'";
                    break;

            }

            Cursor c = sampleDB.rawQuery(zapytanie, null);
            int i = 0;
            while (c.moveToNext()) {
                   tab[i] = String.valueOf(c.getString(1));
                  // showToast(tab[i]);
                   tab1[i] = String.valueOf(c.getString(2))+"     -";
                     //Log.i("myTad",typ +" "+String.valueOf(tab[i]));
                   tab2[i] = String.valueOf(c.getString(3));
                   tab3[i] = String.valueOf(c.getString(4));
                   tab4[i] = String.valueOf(c.getString(5));
                   tab5[i] = String.valueOf(c.getString(6));
                   i++;
            }
            sampleDB.close();
            Custom_row adapter=new Custom_row(this, tab,tab1,tab2,tab3,tab4,tab5);
            plan.setAdapter(adapter);


        } catch (Exception e) {
            showToast("błąd połączenia" + e);
        }

    }

    private void readsqlLight1() {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);

            data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

            zapytanie = "Select * from Plan_zajec where Data >='" + data + "'";

            Cursor c = sampleDB.rawQuery(zapytanie, null);
            int i = 0;
            while (c.moveToNext()) {
                tab[i] = String.valueOf(c.getString(1));
                tab1[i] = String.valueOf(c.getString(2))+"     -";
                tab2[i] = String.valueOf(c.getString(3));
                tab3[i] = String.valueOf(c.getString(4));
                tab4[i] = String.valueOf(c.getString(5));
                tab5[i] = String.valueOf(c.getString(6));
                i++;
            }
            sampleDB.close();
            Custom_row adapter=new Custom_row(this, tab,tab1,tab2,tab3,tab4,tab5);
            plan.setAdapter(adapter);


        } catch (Exception e) {
            showToast("błąd połączenia" + e);
        }

    }


    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        sdf = new SimpleDateFormat(myFormat, Locale.US);
        wartosc.setText(sdf.format(myCalendar.getTime()));
        data = (sdf.format(myCalendar.getTime()));
    }


    private void ToDataBase() {
        try {
            SQLiteDatabase sampleDB = this.openOrCreateDatabase(SAMPLE_DB_NAME, MODE_PRIVATE, null);
            sampleDB.execSQL("CREATE TABLE IF NOT EXISTS Plan_zajec (ID  AUTO_INCREMENT, Data DATE,Czas_s VARCHAR,Sala VARCHAR,Zajecia VARCHAR,Wykladowca VARCHAR,Godzina_f VARCHAR)");
            // showToast("połączenie udało się");

        } catch (Exception e) {
            showToast("błąd połączenia");
        }

    }

    /*
    static void readAndSave(URL url) throws Exception {


        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        url.openStream()   // zwraca InputStream związany z URLem
                )
        );

        String fname = null;
        StringTokenizer st = new StringTokenizer(
                url.getFile(),    // <- zwraca nazwę pliku dla URLa
                "/"                //odcinamy domene pobieramu uri
        );
        System.out.println(url);
        while (st.hasMoreTokens()) fname = st.nextToken(); // pobieramy nazwę pliku
        // pod którą ma być zachowany
        if (fname == null) {
            fname = "index.html";            //jesli fname okaze sie nullem
        }                                  //fname ustawiamy na index.html

        BufferedWriter out = new BufferedWriter(new FileWriter(fname));

        String s;
        while ((s = in.readLine()) != null) {//odczyt ze strumienia i
            out.write(s);                    //zapis do pliku
            out.newLine();
        }
        in.close();        //zamkniecie strumieni
        out.close();
    }
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myCalendar = Calendar.getInstance();
        plan = (ListView) findViewById(R.id.listView);
        zatwierdz = (Button) findViewById(R.id.button);
        tryb = (Spinner) findViewById(R.id.spinner);
        wartosc = (EditText) findViewById(R.id.editText);

        List<String> categories = new ArrayList<String>();
        categories.add("Data");
        categories.add("Godzina");
        categories.add("Przedmiot");

      //  Custom_row adapter=new Custom_row(this, tab,tab1,tab2,tab3,tab4,tab5);
       //plan.setAdapter(adapter);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        tryb.setAdapter(dataAdapter);

        ToDataBase();
        readsqlLight1();

        tryb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                     //   showToast("Data");
                        typ=1;
                        break;
                    case 1:
                       // showToast("Godzina");
                        typ=2;
                        break;
                    case 2:
                     //   showToast("Przedmiot");
                        typ=3;
                        break;
                    default:

                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                showToast("Wybiesz dowolny filtr");
            }
        });


        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

       wartosc.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                   wartosc.setText("");


               switch (typ) {
                   case 1:
                       typ=0;
                   new DatePickerDialog(MainActivity.this, date, myCalendar
                           .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                           myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                       break;

                   case 2:
                       Calendar mcurrentTime = Calendar.getInstance();
                       final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                       final int minute = mcurrentTime.get(Calendar.MINUTE);
                       TimePickerDialog mTimePicker;
                       mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                           @Override
                           public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                               if(selectedHour<10)
                               {
                                   wartosc.setText("0"+ selectedHour + "." + selectedMinute);
                                  // czas=String.valueOf("0"+ selectedHour + " : " + selectedMinute);
                                  // Sczas1 = String.valueOf(selectedHour+"."+ selectedMinute);
                               }
                               if(selectedMinute<10)
                               {
                                   wartosc.setText(selectedHour + "." + "0"+ selectedMinute);
                                 //  czas=String.valueOf(selectedHour + " : " +"0"+ selectedMinute);
                                  // Sczas1 = String.valueOf(selectedHour +"."+ "0"+selectedMinute);
                               }
                               if(selectedHour<10&selectedMinute<10)
                               {
                                   wartosc.setText("0"+selectedHour + "." + "0"+ selectedMinute);
                                  // czas=String.valueOf("0"+selectedHour + " : " +"0"+ selectedMinute);
                                  // Sczas1 = String.valueOf(selectedHour +"."+"0"+ selectedMinute);
                               }
                               if(selectedHour>10&selectedMinute>10) {
                                   wartosc.setText(selectedHour + "." + selectedMinute);
                                 //  czas = String.valueOf(selectedHour + " : " + selectedMinute);
                                  // Sczas1 = String.valueOf(selectedHour +"."+ selectedMinute);
                               }
                           }
                       }, hour, minute, true);//Yes 24 hour time

                       mTimePicker.setTitle("Time Selected");
                       mTimePicker.show();
                       break;

                   case 3:

                       break;

                   default:
                       showToast("Wybierz filtr do zastosowania");
                       break;
               }

            //   String zm=wartosc.getText().toString();

           }
       });

        zatwierdz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                zmienna = wartosc.getText().toString();
                plan.setAdapter(null);



                    for (int i = 0; i<98; i = i + 0) {
                        tab[i] = " ";
                        tab1[i] = " ";
                        tab2[i] = " ";
                        tab3[i] = " ";
                        tab4[i] = " ";
                        tab5[i] = " ";
                        i++;
                     //   Log.i("myTag","dupa"+i);
                    }

               if(!zmienna.equals(""))
               {
                   readsqlLight();
               }
            }
        });


        //pobieranie danych z neta przyszłościowo

        /*
        BufferedReader list = null;// zapisanych w kolejnych liniach
        try {
            list = new BufferedReader(           // lista URLi z pliku url.txt
                    new FileReader("url.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String urlString;
        try {
            while ((urlString = list.readLine()) != null) {
                readAndSave(new URL(urlString)); // tworzony nowy obiekt klasy URL
            }                                // oznaczający zasób z Sieci

            list.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

      //  System.exit(0);
    }

    */
    }

}
