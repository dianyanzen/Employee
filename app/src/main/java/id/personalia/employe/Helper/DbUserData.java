package id.personalia.employe.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import id.personalia.employe.Model.UserData;

import static android.provider.Contacts.SettingsColumns.KEY;
/**
 * Created by Dian Yanzen on 9/27/2017.
 */

public class DbUserData extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "personalia";
    // Contacts table name
    private static final String TABLE_USERDATA = "userdata";
    // UserDataList Table Columns names
    private static final String KEY_EMPLOYEE_ID = "employee_id";
    private static final String KEY_PHOTO = "photo";
    private static final String KEY_ATASAN_ID = "atasan_id";
    private static final String KEY_COMPANY_ID = "company_id";
    private static final String KEY_USER_EMAIL = "user_email";
    private static final String KEY_POSITION_ID = "position_id";
    private static final String KEY_ROLE_ID = "role_id";
    private static final String KEY_ROLE_SALARY_ID = "role_salary_id";
    private static final String KEY_NO_REG = "no_reg";
    private static final String KEY_EMPLOYEE_NAME = "employee_name";
    private static final String KEY_START_WORKING_DT = "start_working_dt";
    private static final String KEY_BORN_PLACE = "born_place";
    private static final String KEY_BORN_DT = "born_dt";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_ADDRESS_STREET = "address_street";
    private static final String KEY_ADDRESS_SUB_DISTRICT = "address_sub_district";
    private static final String KEY_ADDRESS_REGION = "address_region";
    private static final String KEY_ADDRESS_PROVINCE = "address_province";
    private static final String KEY_ADDRESS_COUNTRY = "address_country";
    private static final String KEY_ADDRESS_POSTAL_CODE = "address_postal_code";
    private static final String KEY_HANDPHONE1 = "handphone1";
    private static final String KEY_HANDPHONE2 = "handphone2";
    private static final String KEY_CLOSED_PERSON_NAME = "closed_person_name";
    private static final String KEY_CLOSED_PERSON_PHONE = "closed_person_phone";
    private static final String KEY_MARITAL = "marital";
    private static final String KEY_NPWP_NUMBER = "npwp_number";
    private static final String KEY_BANK_ACCOUNT_NUMBER = "bank_account_number";
    private static final String KEY_WORK_EMAIL = "work_email";
    private static final String KEY_IS_ACTIVE = "is_active";
    private static final String KEY_ID_ATT = "id_att";
    private static final String KEY_RELIGION = "religion";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_MARRIED_STATUS = "married_status";
    private static final String KEY_MARRIED_SINCE = "married_since";
    private static final String KEY_NPWP_DT = "npwp_dt";
    private static final String KEY_BPJS_KETENAGAKERJAAN = "bpjs_ketenagakerjaan";
    private static final String KEY_BPJS_KESEHATAN = "bpjs_kesehatan";
    private static final String KEY_WORK_UNIT_ID = "work_unit_id";
    private static final String KEY_STATUS = "status";
    private static final String KEY_BOLEH_CUTI = "boleh_cuti";
    private static final String KEY_IDENTITY_NO = "identity_no";
    private static final String KEY_CREATED_BY = "created_by";
    private static final String KEY_CREATED_DT = "created_dt";
    private static final String KEY_CHANGED_BY = "changed_by";
    private static final String KEY_CHANGED_DT = "changed_dt";
    private static final String KEY_ROLE_NAME = "role_name";
    private static final String KEY_POSITION_NAME = "position_name";
    private static final String KEY_USER_GROUP_DESCRIPTION = "user_group_description";
    private static final String KEY_USER_GROUP_ID = "user_group_id";
    private static final String KEY_IS_ADMIN = "is_admin";
    private static final String KEY_ATASAN_NAME = "atasan_name";

    public DbUserData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USERDATA + "("
        + KEY_EMPLOYEE_ID + " TEXT PRIMARY KEY," + KEY_PHOTO + " TEXT," + KEY_ATASAN_ID + " TEXT," + KEY_COMPANY_ID + " TEXT," + KEY_USER_EMAIL + " TEXT,"
        + KEY_POSITION_ID + " TEXT ," + KEY_ROLE_ID + " TEXT," + KEY_ROLE_SALARY_ID + " TEXT," + KEY_NO_REG + " TEXT,"
        + KEY_EMPLOYEE_NAME + " TEXT ," + KEY_START_WORKING_DT + " TEXT," + KEY_BORN_PLACE + " TEXT," + KEY_BORN_DT + " TEXT,"
        + KEY_ADDRESS + " TEXT ," + KEY_ADDRESS_STREET + " TEXT," + KEY_ADDRESS_SUB_DISTRICT + " TEXT," + KEY_ADDRESS_REGION + " TEXT,"
        + KEY_ADDRESS_PROVINCE + " TEXT ," + KEY_ADDRESS_COUNTRY + " TEXT," + KEY_ADDRESS_POSTAL_CODE + " TEXT," + KEY_HANDPHONE1 + " TEXT,"
        + KEY_HANDPHONE2 + " TEXT ," + KEY_CLOSED_PERSON_NAME + " TEXT," + KEY_CLOSED_PERSON_PHONE + " TEXT," + KEY_MARITAL + " TEXT,"
        + KEY_NPWP_NUMBER + " TEXT ," + KEY_BANK_ACCOUNT_NUMBER + " TEXT," + KEY_WORK_EMAIL + " TEXT," + KEY_IS_ACTIVE + " TEXT,"
        + KEY_ID_ATT + " TEXT ," + KEY_RELIGION + " TEXT," + KEY_GENDER + " TEXT," + KEY_MARRIED_STATUS + " TEXT,"
        + KEY_MARRIED_SINCE + " TEXT ," + KEY_NPWP_DT + " TEXT," + KEY_BPJS_KETENAGAKERJAAN + " TEXT," + KEY_BPJS_KESEHATAN + " TEXT,"
        + KEY_WORK_UNIT_ID + " TEXT ," + KEY_STATUS + " TEXT," + KEY_BOLEH_CUTI + " TEXT," + KEY_IDENTITY_NO + " TEXT,"
        + KEY_CREATED_BY + " TEXT ," + KEY_CREATED_DT + " TEXT," + KEY_CHANGED_BY + " TEXT," + KEY_CHANGED_DT + " TEXT,"
        + KEY_ROLE_NAME + " TEXT ," + KEY_POSITION_NAME + " TEXT," + KEY_USER_GROUP_DESCRIPTION + " TEXT," + KEY_USER_GROUP_ID +  " TEXT," + KEY_IS_ADMIN + " TEXT," + KEY_ATASAN_NAME + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
        onCreate(db);
    }
    // Adding new UserData
    public void addUserData(UserData userData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMPLOYEE_ID, userData.getEMPLOYEE_ID()); // userData
        values.put(KEY_PHOTO, userData.getPHOTO()); // userData
        values.put(KEY_ATASAN_ID, userData.getATASAN_ID()); // userData
        values.put(KEY_COMPANY_ID, userData.getCOMPANY_ID()); // userData
        values.put(KEY_USER_EMAIL, userData.getUSER_EMAIL()); // userData
        values.put(KEY_POSITION_ID, userData.getPOSITION_ID()); // userData
        values.put(KEY_ROLE_ID, userData.getPHOTO()); // userData
        values.put(KEY_ROLE_SALARY_ID, userData.getROLE_SALARY_ID()); // userData
        values.put(KEY_NO_REG, userData.getNO_REG()); // userData
        values.put(KEY_EMPLOYEE_NAME, userData.getEMPLOYEE_NAME()); // userData
        values.put(KEY_START_WORKING_DT, userData.getSTART_WORKING_DT()); // userData
        values.put(KEY_BORN_PLACE, userData.getBORN_PLACE()); // userData
        values.put(KEY_BORN_DT, userData.getBORN_DT()); // userData
        values.put(KEY_ADDRESS, userData.getADDRESS()); // userData
        values.put(KEY_ADDRESS_STREET, userData.getADDRESS_STREET()); // userData
        values.put(KEY_ADDRESS_SUB_DISTRICT, userData.getADDRESS_SUB_DISTRICT()); // userData
        values.put(KEY_ADDRESS_REGION, userData.getADDRESS_REGION()); // userData
        values.put(KEY_ADDRESS_PROVINCE, userData.getADDRESS_PROVINCE()); // userData
        values.put(KEY_ADDRESS_COUNTRY, userData.getADDRESS_COUNTRY()); // userData
        values.put(KEY_ADDRESS_POSTAL_CODE, userData.getADDRESS_POSTAL_CODE()); // userData
        values.put(KEY_HANDPHONE1, userData.getHANDPHONE1()); // userData
        values.put(KEY_HANDPHONE2, userData.getPHOTO()); // userData
        values.put(KEY_CLOSED_PERSON_NAME, userData.getCLOSED_PERSON_NAME()); // userData
        values.put(KEY_CLOSED_PERSON_PHONE, userData.getCLOSED_PERSON_PHONE()); // userData
        values.put(KEY_MARITAL, userData.getMARITAL()); // userData
        values.put(KEY_NPWP_NUMBER, userData.getNPWP_NUMBER()); // userData
        values.put(KEY_BANK_ACCOUNT_NUMBER, userData.getBANK_ACCOUNT_NUMBER()); // userData
        values.put(KEY_WORK_EMAIL, userData.getWORK_EMAIL()); // userData
        values.put(KEY_IS_ACTIVE, userData.getIS_ACTIVE()); // userData
        values.put(KEY_ID_ATT, userData.getID_ATT()); // userData
        values.put(KEY_RELIGION, userData.getRELIGION()); // userData
        values.put(KEY_GENDER, userData.getGENDER()); // userData
        values.put(KEY_MARRIED_STATUS, userData.getMARRIED_STATUS()); // userData
        values.put(KEY_MARRIED_SINCE, userData.getMARRIED_SINCE()); // userData
        values.put(KEY_NPWP_DT, userData.getNPWP_DT()); // userData
        values.put(KEY_BPJS_KETENAGAKERJAAN, userData.getBPJS_KETENAGAKERJAAN()); // userData
        values.put(KEY_BPJS_KESEHATAN, userData.getBPJS_KESEHATAN()); // userData
        values.put(KEY_WORK_UNIT_ID, userData.getWORK_UNIT_ID()); // userData
        values.put(KEY_STATUS, userData.getSTATUS()); // userData
        values.put(KEY_BOLEH_CUTI, userData.getBOLEH_CUTI()); // userData
        values.put(KEY_IDENTITY_NO, userData.getIDENTITY_NO()); // userData
        values.put(KEY_CREATED_BY, userData.getCREATED_BY()); // userData
        values.put(KEY_CREATED_DT, userData.getCREATED_DT()); // userData
        values.put(KEY_CHANGED_BY, userData.getCHANGED_BY()); // userData
        values.put(KEY_CHANGED_DT, userData.getCHANGED_DT()); // userData
        values.put(KEY_ROLE_NAME, userData.getROLE_NAME()); // userData
        values.put(KEY_POSITION_NAME, userData.getPOSITION_NAME()); // userData
        values.put(KEY_USER_GROUP_DESCRIPTION, userData.getUSER_GROUP_DESCRIPTION()); // userData
        values.put(KEY_USER_GROUP_ID, userData.getUSER_GROUP_ID()); // userData
        values.put(KEY_IS_ADMIN, userData.getIS_ADMIN()); // userData
        values.put(KEY_ATASAN_NAME, userData.getATASAN_NAME()); // userData

// Inserting Row
        db.insert(TABLE_USERDATA, null, values);
        db.close(); // Closing database connection
    }

    public UserData getShop(String employee_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERDATA, new String[] { KEY_EMPLOYEE_ID,
                        KEY_PHOTO, KEY_ATASAN_ID }, KEY_EMPLOYEE_ID + "=?",
                new String[] { employee_id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        UserData UserD = new UserData(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),
                                      cursor.getString(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11),
                                      cursor.getString(12),cursor.getString(13),cursor.getString(14),cursor.getString(15),cursor.getString(16),cursor.getString(17),
                                      cursor.getString(18),cursor.getString(19),cursor.getString(20),cursor.getString(21),cursor.getString(22),cursor.getString(23),
                                      cursor.getString(24),cursor.getString(25),cursor.getString(26),cursor.getString(27),cursor.getString(28),cursor.getString(29),
                                      cursor.getString(30),cursor.getString(31),cursor.getString(32),cursor.getString(33),cursor.getString(34),cursor.getString(35),
                                      cursor.getString(36),cursor.getString(37),cursor.getString(38),cursor.getString(39),cursor.getString(40),cursor.getString(41),
                                      cursor.getString(42),cursor.getString(43),cursor.getString(44),cursor.getString(45),cursor.getString(46),cursor.getString(47),
                                      cursor.getString(48),cursor.getString(49),cursor.getString(50)
                );
// return shop
        return UserD;
    }

    // Getting All UserDataList
    public List<UserData> getAllUserDataList() {
        List<UserData> UserDataList = new ArrayList<UserData>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USERDATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserData UserData = new UserData();
                UserData.setEMPLOYEE_ID(cursor.getString(0));
                UserData.setATASAN_ID(cursor.getString(1));
                UserData.setCOMPANY_ID(cursor.getString(2));
                UserData.setUSER_EMAIL(cursor.getString(3));
                UserData.setPOSITION_ID(cursor.getString(4));
                UserData.setROLE_ID(cursor.getString(5));
                UserData.setROLE_SALARY_ID(cursor.getString(6));
                UserData.setNO_REG(cursor.getString(7));
                UserData.setEMPLOYEE_NAME(cursor.getString(8));
                UserData.setSTART_WORKING_DT(cursor.getString(9));
                UserData.setBORN_PLACE(cursor.getString(10));
                UserData.setBORN_DT(cursor.getString(11));
                UserData.setADDRESS(cursor.getString(12));
                UserData.setADDRESS_STREET(cursor.getString(13));
                UserData.setADDRESS_SUB_DISTRICT(cursor.getString(14));
                UserData.setADDRESS_REGION(cursor.getString(15));
                UserData.setADDRESS_PROVINCE(cursor.getString(16));
                UserData.setADDRESS_COUNTRY(cursor.getString(17));
                UserData.setADDRESS_POSTAL_CODE(cursor.getString(18));
                UserData.setHANDPHONE1(cursor.getString(19));
                UserData.setHANDPHONE2(cursor.getString(20));
                UserData.setCLOSED_PERSON_NAME(cursor.getString(21));
                UserData.setCLOSED_PERSON_PHONE(cursor.getString(22));
                UserData.setMARITAL(cursor.getString(23));
                UserData.setNPWP_NUMBER(cursor.getString(24));
                UserData.setBANK_ACCOUNT_NUMBER(cursor.getString(25));
                UserData.setWORK_EMAIL(cursor.getString(26));
                UserData.setIS_ACTIVE(cursor.getString(27));
                UserData.setPHOTO(cursor.getString(28));
                UserData.setID_ATT(cursor.getString(29));
                UserData.setRELIGION(cursor.getString(30));
                UserData.setGENDER(cursor.getString(31));
                UserData.setMARRIED_STATUS(cursor.getString(32));
                UserData.setMARRIED_SINCE(cursor.getString(33));
                UserData.setNPWP_DT(cursor.getString(34));
                UserData.setBPJS_KETENAGAKERJAAN(cursor.getString(35));
                UserData.setBPJS_KESEHATAN(cursor.getString(36));
                UserData.setWORK_UNIT_ID(cursor.getString(37));
                UserData.setSTATUS(cursor.getString(38));
                UserData.setBOLEH_CUTI(cursor.getString(39));
                UserData.setIDENTITY_NO(cursor.getString(40));
                UserData.setCREATED_BY(cursor.getString(41));
                UserData.setCREATED_DT(cursor.getString(42));
                UserData.setCHANGED_BY(cursor.getString(43));
                UserData.setCHANGED_DT(cursor.getString(44));
                UserData.setROLE_NAME(cursor.getString(45));
                UserData.setPOSITION_NAME(cursor.getString(46));
                UserData.setUSER_GROUP_DESCRIPTION(cursor.getString(47));
                UserData.setUSER_GROUP_ID(cursor.getString(48));
                UserData.setIS_ADMIN(cursor.getString(49));
                UserData.setATASAN_NAME(cursor.getString(50));
// Adding contact to list
                UserDataList.add(UserData);
            } while (cursor.moveToNext());
        }

// return contact list
        return UserDataList;
    }
    // Getting UserData Count
    public int getUserDataListCount() {
        String countQuery = "SELECT * FROM " + TABLE_USERDATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

// return count
        return cursor.getCount();
    }
    // Updating a UserData
    public int updateUserData(UserData UserData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMPLOYEE_ID, UserData.getEMPLOYEE_ID());
        values.put(KEY_PHOTO, UserData.getPHOTO());
        values.put(KEY_ATASAN_ID, UserData.getATASAN_ID());
        values.put(KEY_COMPANY_ID, UserData.getCOMPANY_ID()); // UserData
        values.put(KEY_USER_EMAIL, UserData.getUSER_EMAIL()); // UserData
        values.put(KEY_POSITION_ID, UserData.getPOSITION_ID()); // UserData
        values.put(KEY_ROLE_ID, UserData.getPHOTO()); // UserData
        values.put(KEY_ROLE_SALARY_ID, UserData.getROLE_SALARY_ID()); // UserData
        values.put(KEY_NO_REG, UserData.getNO_REG()); // UserData
        values.put(KEY_EMPLOYEE_NAME, UserData.getEMPLOYEE_NAME()); // UserData
        values.put(KEY_START_WORKING_DT, UserData.getSTART_WORKING_DT()); // UserData
        values.put(KEY_BORN_PLACE, UserData.getBORN_PLACE()); // UserData
        values.put(KEY_BORN_DT, UserData.getBORN_DT()); // UserData
        values.put(KEY_ADDRESS, UserData.getADDRESS()); // UserData
        values.put(KEY_ADDRESS_STREET, UserData.getADDRESS_STREET()); // UserData
        values.put(KEY_ADDRESS_SUB_DISTRICT, UserData.getADDRESS_SUB_DISTRICT()); // UserData
        values.put(KEY_ADDRESS_REGION, UserData.getADDRESS_REGION()); // UserData
        values.put(KEY_ADDRESS_PROVINCE, UserData.getADDRESS_PROVINCE()); // UserData
        values.put(KEY_ADDRESS_COUNTRY, UserData.getADDRESS_COUNTRY()); // UserData
        values.put(KEY_ADDRESS_POSTAL_CODE, UserData.getADDRESS_POSTAL_CODE()); // UserData
        values.put(KEY_HANDPHONE1, UserData.getHANDPHONE1()); // UserData
        values.put(KEY_HANDPHONE2, UserData.getPHOTO()); // UserData
        values.put(KEY_CLOSED_PERSON_NAME, UserData.getCLOSED_PERSON_NAME()); // UserData
        values.put(KEY_CLOSED_PERSON_PHONE, UserData.getCLOSED_PERSON_PHONE()); // UserData
        values.put(KEY_MARITAL, UserData.getMARITAL()); // UserData
        values.put(KEY_NPWP_NUMBER, UserData.getNPWP_NUMBER()); // UserData
        values.put(KEY_BANK_ACCOUNT_NUMBER, UserData.getBANK_ACCOUNT_NUMBER()); // UserData
        values.put(KEY_WORK_EMAIL, UserData.getWORK_EMAIL()); // UserData
        values.put(KEY_IS_ACTIVE, UserData.getIS_ACTIVE()); // UserData
        values.put(KEY_ID_ATT, UserData.getID_ATT()); // UserData
        values.put(KEY_RELIGION, UserData.getRELIGION()); // UserData
        values.put(KEY_GENDER, UserData.getGENDER()); // UserData
        values.put(KEY_MARRIED_STATUS, UserData.getMARRIED_STATUS()); // UserData
        values.put(KEY_MARRIED_SINCE, UserData.getMARRIED_SINCE()); // UserData
        values.put(KEY_NPWP_DT, UserData.getNPWP_DT()); // UserData
        values.put(KEY_BPJS_KETENAGAKERJAAN, UserData.getBPJS_KETENAGAKERJAAN()); // UserData
        values.put(KEY_BPJS_KESEHATAN, UserData.getBPJS_KESEHATAN()); // UserData
        values.put(KEY_WORK_UNIT_ID, UserData.getWORK_UNIT_ID()); // UserData
        values.put(KEY_STATUS, UserData.getSTATUS()); // UserData
        values.put(KEY_BOLEH_CUTI, UserData.getBOLEH_CUTI()); // UserData
        values.put(KEY_IDENTITY_NO, UserData.getIDENTITY_NO()); // UserData
        values.put(KEY_CREATED_BY, UserData.getCREATED_BY()); // UserData
        values.put(KEY_CREATED_DT, UserData.getCREATED_DT()); // UserData
        values.put(KEY_CHANGED_BY, UserData.getCHANGED_BY()); // UserData
        values.put(KEY_CHANGED_DT, UserData.getCHANGED_DT()); // UserData
        values.put(KEY_ROLE_NAME, UserData.getROLE_NAME()); // UserData
        values.put(KEY_POSITION_NAME, UserData.getPOSITION_NAME()); // UserData
        values.put(KEY_USER_GROUP_DESCRIPTION, UserData.getUSER_GROUP_DESCRIPTION()); // UserData
        values.put(KEY_USER_GROUP_ID, UserData.getUSER_GROUP_ID()); // UserData
        values.put(KEY_IS_ADMIN, UserData.getIS_ADMIN()); // UserData
        values.put(KEY_ATASAN_NAME, UserData.getATASAN_NAME()); // UserData

// updating row
        return db.update(TABLE_USERDATA, values, KEY_EMPLOYEE_ID + " = ?",
        new String[]{UserData.getEMPLOYEE_ID()});
    }

    // Deleting a UserData
    public void deleteUserData(UserData UserData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERDATA, KEY_EMPLOYEE_ID + " = ?",
        new String[] { UserData.getEMPLOYEE_ID() });
        db.close();
    }
    public void deleteAllUserData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_USERDATA);
        db.close();
    }
}