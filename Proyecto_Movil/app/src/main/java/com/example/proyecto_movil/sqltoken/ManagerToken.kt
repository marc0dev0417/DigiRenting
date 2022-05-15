package com.example.proyecto_movil.sqltoken

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyecto_movil.model.UserDataSQL

class ManagerToken(
    context: Context?,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "user_digi"
        private const val TABLE_USER = "users"
        private const val KEY_ID = "user_id"
        private const val COL_TOKEN = "token"
        private const val COL_TOKEN_EXPIRED = "token_date_expired"
        private const val COL_USER_NAME = "user_name"
    }

    override fun onCreate(dataBaseSQL: SQLiteDatabase?) {
      val sqlCreateTable = "CREATE TABLE $TABLE_USER ($KEY_ID INTEGER PRIMARY KEY, $COL_TOKEN TEXT, $COL_TOKEN_EXPIRED TEXT, $COL_USER_NAME TEXT)"

        // at last we are calling a exec sql
        // method to execute above sql query
        dataBaseSQL?.execSQL(sqlCreateTable)
    }
    override fun onUpgrade(dataBaseSQL: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dataBaseSQL?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(dataBaseSQL)
    }

    fun addUserWithToken(keyId: Int, token: String, tokenExpired: String, username: String){
        val databaseSQL = this.writableDatabase
        val values = ContentValues()

        values.put(KEY_ID, keyId)
        values.put(COL_TOKEN, token)
        values.put(COL_TOKEN_EXPIRED, tokenExpired)
        values.put(COL_USER_NAME, username)

        databaseSQL.insert(TABLE_USER, null, values)

        databaseSQL.close()
    }
    fun deleteUserWithToken(keyId: Int): Boolean{

        val dataBaseSQL = this.writableDatabase

        return dataBaseSQL.delete(TABLE_USER, "$KEY_ID = $keyId", null) > 0
    }

    @SuppressLint("Range")
    fun viewUserWithToken(): MutableList<UserDataSQL>{
        val listUserDataSQL: MutableList<UserDataSQL> = mutableListOf()

        val selectQuery = "SELECT * FROM $TABLE_USER"
        val dataBaseSQL = this.readableDatabase

        var cursor: Cursor? = null

        try{
            cursor = dataBaseSQL.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            dataBaseSQL.execSQL(selectQuery)
            return ArrayList()
        }
        var idUser: Int
        var token: String
        var tokenExpired: String
        var username: String

        if(cursor.moveToFirst()){

            do {
                idUser = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                token = cursor.getString(cursor.getColumnIndex(COL_TOKEN))
                tokenExpired = cursor.getString(cursor.getColumnIndex(COL_TOKEN_EXPIRED))
                username = cursor.getString(cursor.getColumnIndex(COL_USER_NAME))

                listUserDataSQL.add(UserDataSQL(idUser, username, token, tokenExpired))
            }while (cursor.moveToNext())
        }
        return listUserDataSQL
    }
}