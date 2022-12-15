package com.example.projetandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {



        private static final String DATABASE_NAME = "projetdb";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_USER = "user";

        private static final String KEY_USERNAME = "username";
        private static final String KEY_PASSWORD = "password";


        private static final String TABLE_PERSONNE = "personne";

        private static final String KEY_AGE = "age";
        private static final String KEY_GENRE= "genre";
        private static final String KEY_BLOODPRESSURE = "bloodPressure";
        private static final String KEY_CHOLESTEROL = "cholesterol";
        private static final String KEY_NA = "na";
        private static final String KEY_K = "k";


        public DatabaseHandler(Context context) {
                super(context,DATABASE_NAME,null,DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

                String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                        + KEY_USERNAME + " TEXT PRIMARY KEY,"
                        + KEY_PASSWORD + " TEXT"
                        +")";

                String CREATE_PERSONNE_TABLE = "CREATE TABLE " + TABLE_PERSONNE + "("
                        + KEY_USERNAME + " TEXT,"
                        + KEY_PASSWORD + " TEXT,"
                        + KEY_AGE + " INTEGER,"
                        + KEY_GENRE + " TEXT,"
                        + KEY_BLOODPRESSURE + " TEXT,"
                        + KEY_CHOLESTEROL + " TEXT,"
                        + KEY_NA + " INTEGER,"
                        + KEY_K + " INTEGER,"
                        + "FOREIGN KEY(" + KEY_USERNAME + ") REFERENCES "+ TABLE_USER +" (" + KEY_USERNAME + ") ON DELETE CASCADE,"
                        + "FOREIGN KEY(" + KEY_PASSWORD + ") REFERENCES "+ TABLE_USER +" (" + KEY_PASSWORD + ") ON DELETE CASCADE"
                        + ")";

                db.execSQL(CREATE_USER_TABLE);
                db.execSQL(CREATE_PERSONNE_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion , int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONNE);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

                onCreate(db);
        }

        void addUser(User user){
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(KEY_USERNAME,user.getUsername());
                values.put(KEY_PASSWORD,user.getPassword());

                db.insert(TABLE_USER,null,values);
                db.close();

        }

        void addPesonne(Personne personne){

                User user = new User(personne.getUsername(),personne.getPassword());
                addUser(user);

                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(KEY_AGE,personne.getAge());
                values.put(KEY_GENRE,personne.getGenre());
                values.put(KEY_BLOODPRESSURE,personne.getBloodPressure());
                values.put(KEY_CHOLESTEROL,personne.getCholesterol());
                values.put(KEY_NA,personne.getNa());
                values.put(KEY_K,personne.getK());

                values.put(KEY_USERNAME,user.getUsername());
                values.put(KEY_PASSWORD,user.getPassword());

                db.insert(TABLE_PERSONNE,null,values);

                db.close();


        }

        User getUser(String username){
                SQLiteDatabase db = this.getReadableDatabase();

                Cursor cursor = db.query(TABLE_USER,new String[] {KEY_USERNAME,KEY_PASSWORD},
                        KEY_USERNAME + "=?",
                        new String[] {String.valueOf(username)},null,null,null,null);

                if(cursor != null)
                        cursor.moveToFirst();

                assert cursor != null;

                User user = new User();

                user.setUsername(String.valueOf(cursor.getString(0)));
                user.setPassword(String.valueOf(cursor.getString(1)));

                cursor.close();

                return user;
        }


        Personne getPersonne(String username){

                SQLiteDatabase db = this.getReadableDatabase();

                Cursor cursor = db.query(TABLE_PERSONNE,new String[] {
                                KEY_USERNAME,KEY_PASSWORD,KEY_AGE,
                                KEY_GENRE,KEY_BLOODPRESSURE,
                                KEY_CHOLESTEROL,KEY_NA,KEY_K},
                        KEY_USERNAME + "=?",
                        new String[] {String.valueOf(username)},null,null,null,null);

                if(cursor != null)
                        cursor.moveToFirst();


                assert cursor != null;

                Personne personne = new Personne();

                personne.setUsername(String.valueOf(cursor.getString(0)));
                personne.setPassword(String.valueOf(cursor.getString(1)));
                personne.setAge(Integer.parseInt(cursor.getString(2)));
                personne.setGenre(String.valueOf(cursor.getString(3)));
                personne.setBloodPressure(String.valueOf(cursor.getString(4)));
                personne.setCholesterol(String.valueOf(cursor.getString(5)));
                personne.setNa(Integer.parseInt(cursor.getString(6)));
                personne.setK(Integer.parseInt(cursor.getString(7)));

                cursor.close();

                return personne;


        }

        public List<User> getAllUsers(){
                List<User> userList = new ArrayList<>();

                String selectQuery = "SELECT * FROM " + TABLE_USER;

                SQLiteDatabase db = this.getWritableDatabase();

                Cursor cursor = db.rawQuery(selectQuery,null);

                if(cursor.moveToFirst()){
                        do {
                                User user = new User();

                                user.setUsername(cursor.getString(0));
                                user.setPassword(cursor.getString(1));

                                userList.add(user);

                        }while (cursor.moveToNext());
                }
                cursor.close();

                return userList;
        }

        public List<Personne> getAllPersonnes(){

                List<Personne> personneList = new ArrayList<>();

                String selectQuery = "SELECT * FROM " + TABLE_PERSONNE;

                SQLiteDatabase db = this.getWritableDatabase();

                Cursor cursor = db.rawQuery(selectQuery,null);

                if(cursor.moveToFirst()){
                        do {
                                Personne personne = new Personne();

                                personne.setUsername(String.valueOf(cursor.getString(0)));
                                personne.setPassword(String.valueOf(cursor.getString(1)));
                                personne.setAge(Integer.parseInt(cursor.getString(2)));
                                personne.setGenre(String.valueOf(cursor.getString(3)));
                                personne.setBloodPressure(String.valueOf(cursor.getString(4)));
                                personne.setCholesterol(String.valueOf(cursor.getString(5)));
                                personne.setNa(Integer.parseInt(cursor.getString(6)));
                                personne.setK(Integer.parseInt(cursor.getString(7)));

                                personneList.add(personne);

                        }while (cursor.moveToNext());
                }
                cursor.close();

                return personneList;
        }

        public int updateUser(User user){
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();

                values.put(KEY_USERNAME,user.getUsername());
                values.put(KEY_PASSWORD,user.getPassword());

                return db.update(TABLE_USER,values,KEY_USERNAME + "=?",
                        new String[] {String.valueOf(user.getUsername())});
        }

        public int updatePersonne(Personne personne){
                SQLiteDatabase db = this.getWritableDatabase();

                ContentValues values = new ContentValues();

                values.put(KEY_USERNAME,personne.getUsername());
                values.put(KEY_PASSWORD,personne.getPassword());
                values.put(KEY_AGE,personne.getAge());
                values.put(KEY_GENRE,personne.getGenre());
                values.put(KEY_BLOODPRESSURE,personne.getBloodPressure());
                values.put(KEY_CHOLESTEROL,personne.getCholesterol());
                values.put(KEY_NA,personne.getNa());
                values.put(KEY_K,personne.getK());

                return db.update(TABLE_PERSONNE,values,KEY_USERNAME + "=?",
                        new String[] {String.valueOf(personne.getUsername())});

        }


        public void deleteUser(User user){
                SQLiteDatabase db = this.getWritableDatabase();

                db.delete(TABLE_USER,KEY_USERNAME + "=?",
                        new String[] {String.valueOf(user.getUsername())});

                db.close();

        }

        public void deletePersonne(Personne personne){
                SQLiteDatabase db = this.getWritableDatabase();

                db.delete(TABLE_PERSONNE,KEY_USERNAME + "=?",
                        new String[] {String.valueOf(personne.getUsername())});

                db.close();

        }

        public int getUsersCount(){
                String countQuery = "SELECT * FROM " + TABLE_USER;
                SQLiteDatabase db = this.getWritableDatabase();
                Cursor cursor = db.rawQuery(countQuery,null);
                cursor.close();

                return cursor.getCount();

        }

        public int getPersonnesCount(){
                String countQuery = "SELECT * FROM " + TABLE_PERSONNE;
                SQLiteDatabase db = this.getWritableDatabase();
                Cursor cursor = db.rawQuery(countQuery,null);
                cursor.close();

                return cursor.getCount();

        }

}
