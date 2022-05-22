package com.example.proyecto_movil.sqltoken

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyecto_movil.model.FavoriteDataSQL
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
        private const val COL_FIRSTNAME = "firstname"
        private const val COL_LASTNAME = "lastname"
        private const val COL_USER_NAME = "username"
        private const val COL_MAIL = "mail"
        private const val COL_ADDRESS = "address"
        private const val COL_PASSWORD = "password"

        private const val TABLE_HOUSE = "favorites"
        private const val KEY_ID_HOUSE = "house_id"
        private const val COL_OWNER = "owner"
        private const val COL_URL = "url"
        private const val COL_REGION = "region"
        private const val COL_PRICE = "price"
    }

    override fun onCreate(dataBaseSQL: SQLiteDatabase?) {
        val sqlCreateTable = "CREATE TABLE $TABLE_USER ($KEY_ID INTEGER PRIMARY KEY, $COL_TOKEN TEXT, $COL_TOKEN_EXPIRED TEXT, $COL_FIRSTNAME TEXT, $COL_LASTNAME TEXT , $COL_USER_NAME TEXT, $COL_MAIL TEXT, $COL_ADDRESS TEXT, $COL_PASSWORD TEXT)"
        // at last we are calling a exec sql
        // method to execute above sql query
        dataBaseSQL?.execSQL(sqlCreateTable)

        val sqlCreateTableFavorite = "CREATE TABLE $TABLE_HOUSE ($KEY_ID_HOUSE INTEGER PRIMARY KEY, $COL_OWNER TEXT, $COL_URL TEXT, $COL_REGION TEXT, $COL_PRICE DOUBLE, $KEY_ID INTEGER,  FOREIGN KEY($KEY_ID) REFERENCES $TABLE_USER($KEY_ID))"
        dataBaseSQL?.execSQL(sqlCreateTableFavorite)
    }
    override fun onUpgrade(dataBaseSQL: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        dataBaseSQL?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        dataBaseSQL?.execSQL("DROP TABLE IF EXISTS $TABLE_HOUSE")
        onCreate(dataBaseSQL)
    }

    fun addUserWithToken(keyId: Int, token: String, tokenExpired: String, firstname: String, lastname: String, username: String, mail: String, address: String, password: String){
        val databaseSQL = this.writableDatabase
        val values = ContentValues()

        values.put(KEY_ID, keyId)
        values.put(COL_TOKEN, token)
        values.put(COL_TOKEN_EXPIRED, tokenExpired)
        values.put(COL_FIRSTNAME, firstname)
        values.put(COL_LASTNAME, lastname)
        values.put(COL_USER_NAME, username)
        values.put(COL_MAIL, mail)
        values.put(COL_ADDRESS, address)
        values.put(COL_PASSWORD, password)

        databaseSQL.insert(TABLE_USER, null, values)

        databaseSQL.close()
    }
    fun addFavorite(houseId: Int, owner: String, url: String, region: String, price: Double, userId: Int) {

        val dataBaseSQL = this.writableDatabase
        val values = ContentValues()

        values.put(KEY_ID_HOUSE, houseId)
        values.put(COL_OWNER, owner)
        values.put(COL_URL, url)
        values.put(COL_REGION, region)
        values.put(COL_PRICE, price)
        values.put(KEY_ID, userId)

        dataBaseSQL.insert(TABLE_HOUSE, null, values)
        dataBaseSQL.close()
    }
    fun deleteFavorite(keyHouseId: Int): Boolean{

        val dataBaseSQL = this.writableDatabase

        return dataBaseSQL.delete(TABLE_HOUSE, "$KEY_ID_HOUSE = $keyHouseId", null) > 0
    }
    fun updateUserWithToken(token: String, tokenExpired: String, firstname: String, lastname: String, username: String, mail: String, address: String, password: String){
      val databaseSQL = this.readableDatabase

      databaseSQL.execSQL("UPDATE $TABLE_USER SET $COL_TOKEN = $token, $COL_TOKEN_EXPIRED = $tokenExpired, $COL_FIRSTNAME = $firstname, $COL_LASTNAME = $lastname, $COL_USER_NAME = $username, $COL_MAIL = $mail, $COL_ADDRESS = $address, $COL_PASSWORD = $password")
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
        var firstname: String
        var lastname: String
        var username: String
        var mail: String
        var address: String
        var password: String

        if(cursor.moveToFirst()){
            do {
                idUser = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                token = cursor.getString(cursor.getColumnIndex(COL_TOKEN))
                tokenExpired = cursor.getString(cursor.getColumnIndex(COL_TOKEN_EXPIRED))
                firstname = cursor.getString(cursor.getColumnIndex(COL_FIRSTNAME))
                lastname = cursor.getString(cursor.getColumnIndex(COL_LASTNAME))
                username = cursor.getString(cursor.getColumnIndex(COL_USER_NAME))
                mail = cursor.getString(cursor.getColumnIndex(COL_MAIL))
                address = cursor.getString(cursor.getColumnIndex(COL_ADDRESS))
                password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))

                listUserDataSQL.add(UserDataSQL(idUser,firstname,lastname,username,mail,address,password,token,tokenExpired))
            }while (cursor.moveToNext())
        }
        return listUserDataSQL
    }
    @SuppressLint("Range")
    fun viewHouseFavorite(): MutableList<FavoriteDataSQL>{
        var listHouseFavorite: MutableList<FavoriteDataSQL> = mutableListOf()

        val selectQuery = "SELECT * FROM $TABLE_HOUSE"
        val dataBaseSQL = this.readableDatabase

        var cursor: Cursor? = null

        try{
            cursor = dataBaseSQL.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            dataBaseSQL.execSQL(selectQuery)
            return ArrayList()
        }
        var keyIdHouse: Int
        var owner: String
        var url: String
        var region: String
        var price: Double
        var keyUserId: Int

        if(cursor.moveToFirst()){
            do {
                keyIdHouse = cursor.getInt(cursor.getColumnIndex(KEY_ID_HOUSE))
                owner = cursor.getString(cursor.getColumnIndex(COL_OWNER))
                url = cursor.getString(cursor.getColumnIndex(COL_URL))
                region = cursor.getString(cursor.getColumnIndex(COL_REGION))
                price = cursor.getDouble(cursor.getColumnIndex(COL_PRICE))
                keyUserId = cursor.getInt(cursor.getColumnIndex(KEY_ID))

                listHouseFavorite.add(FavoriteDataSQL(keyIdHouse, owner, url, region, price, keyUserId))

            }while (cursor.moveToNext())
        }
        return listHouseFavorite
    }
}