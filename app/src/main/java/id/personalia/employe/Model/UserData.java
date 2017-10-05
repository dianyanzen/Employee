package id.personalia.employe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dian Yanzen on 9/13/2017.
 */

public class UserData {
    @SerializedName("employee_id")
    @Expose
    private String employee_id;

    @SerializedName("atasan_id")
    @Expose
    private String atasan_id;

    @SerializedName("company_id")
    @Expose
    private String company_id;

    @SerializedName("user_email")
    @Expose
    private String user_email;

    @SerializedName("position_id")
    @Expose
    private String position_id;

    @SerializedName("role_id")
    @Expose
    private String role_id;

    @SerializedName("role_salary_id")
    @Expose
    private String role_salary_id;

    @SerializedName("no_reg")
    @Expose
    private String no_reg;

    @SerializedName("employee_name")
    @Expose
    private String employee_name;

    @SerializedName("start_working_dt")
    @Expose
    private String start_working_dt;

    @SerializedName("born_place")
    @Expose
    private String born_place;

    @SerializedName("born_dt")
    @Expose
    private String born_dt;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("address_street")
    @Expose
    private String address_street;

    @SerializedName("address_region")
    @Expose
    private String address_region;

    @SerializedName("address_sub_district")
    @Expose
    private String address_sub_district;

    @SerializedName("address_province")
    @Expose
    private String address_province;

    @SerializedName("address_country")
    @Expose
    private String address_country;

    @SerializedName("address_postal_code")
    @Expose
    private String address_postal_code;

    @SerializedName("handphone1")
    @Expose
    private String handphone1;

    @SerializedName("handphone2")
    @Expose
    private String handphone2;

    @SerializedName("closed_person_name")
    @Expose
    private String closed_person_name;

    @SerializedName("closed_person_phone")
    @Expose
    private String closed_person_phone;

    @SerializedName("marital")
    @Expose
    private String marital;

    @SerializedName("npwp_number")
    @Expose
    private String npwp_number;

    @SerializedName("bank_account_number")
    @Expose
    private String bank_account_number;

    @SerializedName("work_email")
    @Expose
    private String work_email;

    @SerializedName("is_active")
    @Expose
    private String is_active;

    @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("id_att")
    @Expose
    private String id_att;

    @SerializedName("religion")
    @Expose
    private String religion;

    @SerializedName("gender")
    @Expose
    private String gender;

    @SerializedName("married_status")
    @Expose
    private String married_status;

    @SerializedName("married_since")
    @Expose
    private String married_since;

    @SerializedName("npwp_dt")
    @Expose
    private String npwp_dt;

    @SerializedName("bpjs_ketenagakerjaan")
    @Expose
    private String bpjs_ketenagakerjaan;

    @SerializedName("bpjs_kesehatan")
    @Expose
    private String bpjs_kesehatan;

    @SerializedName("work_unit_id")
    @Expose
    private String work_unit_id;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("boleh_cuti")
    @Expose
    private String boleh_cuti;

    @SerializedName("identity_no")
    @Expose
    private String identity_no;

    @SerializedName("created_by")
    @Expose
    private String created_by;

    @SerializedName("created_dt")
    @Expose
    private String created_dt;

    @SerializedName("changed_by")
    @Expose
    private String changed_by;

    @SerializedName("changed_dt")
    @Expose
    private String changed_dt;

    @SerializedName("role_name")
    @Expose
    private String role_name;

    @SerializedName("position_name")
    @Expose
    private String position_name;

    @SerializedName("user_group_description")
    @Expose
    private String user_group_description;

    @SerializedName("user_group_id")
    @Expose
    private String user_group_id;

    @SerializedName("is_admin")
    @Expose
    private String is_admin;

    @SerializedName("atasan_name")
    @Expose
    private String atasan_name;
    // constructors
    public UserData() {
    }

    public UserData(String employee_id, String photo,String atasan_id,String company_id,String user_email,
                    String position_id,String role_id,String role_salary_id,String no_reg,
                    String employee_name,String start_working_dt,String born_place,String born_dt,
                    String address,String address_street,String address_region,String address_sub_district,
                    String address_province,String address_country,String address_postal_code,
                    String handphone1,String handphone2,String closed_person_name,String closed_person_phone,
                    String marital,String npwp_number,String bank_account_number,String work_email,
                    String is_active,String id_att,String religion,String gender,String married_status,
                    String married_since,String npwp_dt,String bpjs_ketenagakerjaan,String bpjs_kesehatan,
                    String work_unit_id,String status,String boleh_cuti,String identity_no,String created_by,
                    String created_dt,String changed_by,String changed_dt,String role_name,
                    String position_name,String user_group_description,String user_group_id,String is_admin,String atasan_name
                    ) {
        this.employee_id = employee_id;
        this.photo = photo;
        this.atasan_id = atasan_id;
        this.company_id = company_id;
        this.user_email = user_email;
        this.position_id = position_id;
        this.role_id = role_id;
        this.role_salary_id = role_salary_id;
        this.no_reg = no_reg;
        this.employee_name = employee_name;
        this.start_working_dt = start_working_dt;
        this.born_place = born_place;
        this.born_dt = born_dt;
        this.address = address;
        this.address_street = address_street;
        this.address_region = address_region;
        this.address_sub_district = address_sub_district;
        this.address_province = address_province;
        this.address_country = address_country;
        this.address_postal_code = address_postal_code;
        this.handphone1 = handphone1;
        this.handphone2 = handphone2;
        this.closed_person_name = closed_person_name;
        this.closed_person_phone = closed_person_phone;
        this.marital = marital;
        this.npwp_number = npwp_number;
        this.bank_account_number = bank_account_number;
        this.work_email = work_email;
        this.is_active = is_active;
        this.id_att = id_att;
        this.religion = religion;
        this.gender = gender;
        this.married_status = married_status;
        this.married_since = married_since;
        this.npwp_dt = npwp_dt;
        this.bpjs_ketenagakerjaan = bpjs_ketenagakerjaan;
        this.bpjs_kesehatan = bpjs_kesehatan;
        this.work_unit_id = work_unit_id;
        this.status = status;
        this.boleh_cuti = boleh_cuti;
        this.identity_no = identity_no;
        this.created_by = created_by;
        this.created_dt = created_dt;
        this.changed_by = changed_by;
        this.changed_dt = changed_dt;
        this.role_name = role_name;
        this.position_name = position_name;
        this.user_group_description = user_group_description;
        this.user_group_id = user_group_id;
        this.is_admin = is_admin;
        this.atasan_name = atasan_name;
    }

    //All Function
    public String getEMPLOYEE_ID() {
        return employee_id;
    }

    public void setEMPLOYEE_ID(String employee_id) {

        this.employee_id = employee_id;
    }

    public String getPHOTO() {
        return photo;
    }

    public void setPHOTO(String photo) {

        this.photo = photo;
    }

    public String getATASAN_ID() { return atasan_id; }

    public void setATASAN_ID(String atasan_id) {

        this.atasan_id = atasan_id;
    }

    public String getCOMPANY_ID() { return company_id; }

    public void setCOMPANY_ID(String company_id) {

        this.company_id = company_id;
    }

    public String getUSER_EMAIL() {
        return user_email;
    }

    public void setUSER_EMAIL(String user_email) {

        this.user_email = user_email;
    }

    public String getPOSITION_ID() {
        return position_id;
    }

    public void setPOSITION_ID(String position_id) {

        this.position_id = position_id;
    }

    public String getROLE_ID() {
        return role_id;
    }

    public void setROLE_ID(String role_id) {

        this.role_id = role_id;
    }

    public String getROLE_SALARY_ID() {
        return role_salary_id;
    }

    public void setROLE_SALARY_ID(String role_salary_id) {

        this.role_salary_id = role_salary_id;
    }

    public String getNO_REG() {
        return no_reg;
    }

    public void setNO_REG(String no_reg) {

        this.no_reg = no_reg;
    }

    public String getEMPLOYEE_NAME() {
        return employee_name;
    }

    public void setEMPLOYEE_NAME(String employee_name) {

        this.employee_name = employee_name;
    }

    public String getSTART_WORKING_DT() {
        return start_working_dt;
    }

    public void setSTART_WORKING_DT(String start_working_dt) {

        this.start_working_dt = start_working_dt;
    }

    public String getBORN_PLACE() {
        return born_place;
    }

    public void setBORN_PLACE(String born_place) {

        this.born_place = born_place;
    }

    public String getBORN_DT() {
        return born_dt;
    }

    public void setBORN_DT(String born_dt) {

        this.born_dt = born_dt;
    }

    public String getADDRESS() {
        return address;
    }

    public void setADDRESS(String address) {

        this.address = address;
    }

    public String getADDRESS_STREET() {
        return address_street;
    }

    public void setADDRESS_STREET(String address_street) {

        this.address_street = address_street;
    }

    public String getADDRESS_REGION() {
        return address_region;
    }

    public void setADDRESS_REGION(String address_region) {

        this.address_region = address_region;
    }

    public String getADDRESS_SUB_DISTRICT() {
        return address_sub_district;
    }

    public void setADDRESS_SUB_DISTRICT(String address_sub_district) {

        this.address_sub_district = address_sub_district;
    }

    public String getADDRESS_PROVINCE() {
        return address_province;
    }

    public void setADDRESS_PROVINCE(String address_province) {

        this.address_province = address_province;
    }

    public String getADDRESS_COUNTRY() {
        return address_country;
    }

    public void setADDRESS_COUNTRY(String address_country) {

        this.address_country = address_country;
    }

    public String getADDRESS_POSTAL_CODE() {
        return address_postal_code;
    }

    public void setADDRESS_POSTAL_CODE(String address_postal_code) {

        this.address_postal_code = address_postal_code;
    }

    public String getHANDPHONE1() {
        return handphone1;
    }

    public void setHANDPHONE1(String handphone1) {

        this.handphone1 = handphone1;
    }

    public String getHANDPHONE2() {
        return handphone2;
    }

    public void setHANDPHONE2(String handphone2) {

        this.handphone2 = handphone2;
    }

    public String getCLOSED_PERSON_NAME() {
        return closed_person_name;
    }

    public void setCLOSED_PERSON_NAME(String closed_person_name) {

        this.closed_person_name = closed_person_name;
    }

    public String getCLOSED_PERSON_PHONE() {
        return closed_person_phone;
    }

    public void setCLOSED_PERSON_PHONE(String closed_person_phone) {

        this.closed_person_phone = closed_person_phone;
    }

    public String getMARITAL() {
        return marital;
    }

    public void setMARITAL(String marital) {

        this.marital = marital;
    }

    public String getNPWP_NUMBER() {
        return npwp_number;
    }

    public void setNPWP_NUMBER(String npwp_number) {

        this.npwp_number = npwp_number;
    }

    public String getBANK_ACCOUNT_NUMBER() {
        return bank_account_number;
    }

    public void setBANK_ACCOUNT_NUMBER(String bank_account_number) {

        this.bank_account_number = bank_account_number;
    }

    public String getWORK_EMAIL() {
        return work_email;
    }

    public void setWORK_EMAIL(String work_email) {

        this.work_email = work_email;
    }

    public String getIS_ACTIVE() {
        return is_active;
    }

    public void setIS_ACTIVE(String is_active) {

        this.is_active = is_active;
    }

    public String getID_ATT() {
        return id_att;
    }

    public void setID_ATT(String id_att) {

        this.id_att = id_att;
    }

    public String getRELIGION() {
        return religion;
    }

    public void setRELIGION(String religion) {

        this.religion = religion;
    }

    public String getGENDER() {
        return gender;
    }

    public void setGENDER(String gender) {

        this.gender = gender;
    }

    public String getMARRIED_STATUS() {
        return married_status;
    }

    public void setMARRIED_STATUS(String married_status) {

        this.married_status = married_status;
    }

    public String getMARRIED_SINCE() {
        return married_since;
    }

    public void setMARRIED_SINCE(String married_since) {

        this.married_since = married_since;
    }

    public String getNPWP_DT() {
        return npwp_dt;
    }

    public void setNPWP_DT(String npwp_dt) {

        this.npwp_dt = npwp_dt;
    }

    public String getBPJS_KETENAGAKERJAAN() {
        return bpjs_ketenagakerjaan;
    }

    public void setBPJS_KETENAGAKERJAAN(String bpjs_ketenagakerjaan) {

        this.bpjs_ketenagakerjaan = bpjs_ketenagakerjaan;
    }

    public String getBPJS_KESEHATAN() {
        return bpjs_kesehatan;
    }

    public void setBPJS_KESEHATAN(String bpjs_kesehatan) {

        this.bpjs_kesehatan = bpjs_kesehatan;
    }

    public String getWORK_UNIT_ID() {
        return work_unit_id;
    }

    public void setWORK_UNIT_ID(String work_unit_id) {

        this.work_unit_id = work_unit_id;
    }

    public String getSTATUS() {
        return status;
    }

    public void setSTATUS(String status) {

        this.status = status;
    }

    public String getBOLEH_CUTI() {
        return boleh_cuti;
    }

    public void setBOLEH_CUTI(String boleh_cuti) {

        this.boleh_cuti = boleh_cuti;
    }

    public String getIDENTITY_NO() {
        return identity_no;
    }

    public void setIDENTITY_NO(String identity_no) {

        this.identity_no = identity_no;
    }

    public String getCREATED_BY() {
        return created_by;
    }

    public void setCREATED_BY(String created_by) {

        this.created_by = created_by;
    }

    public String getCREATED_DT() {
        return created_dt;
    }

    public void setCREATED_DT(String created_dt) {

        this.created_dt = created_dt;
    }

    public String getCHANGED_BY() {
        return changed_by;
    }

    public void setCHANGED_BY(String changed_by) {

        this.changed_by = changed_by;
    }

    public String getCHANGED_DT() {
        return changed_dt;
    }

    public void setCHANGED_DT(String changed_dt) {

        this.changed_dt = changed_dt;
    }

    public String getROLE_NAME() {
        return role_name;
    }

    public void setROLE_NAME(String role_name) {

        this.role_name = role_name;
    }

    public String getPOSITION_NAME() {
        return position_name;
    }

    public void setPOSITION_NAME(String position_name) {

        this.position_name = position_name;
    }

    public String getUSER_GROUP_DESCRIPTION() {
        return user_group_description;
    }

    public void setUSER_GROUP_DESCRIPTION(String user_group_description) {

        this.user_group_description = user_group_description;
    }

    public String getUSER_GROUP_ID() { return user_group_id; }

    public void setUSER_GROUP_ID(String user_group_id) {

        this.user_group_id = user_group_id;
    }

    public String getIS_ADMIN() { return is_admin; }

    public void setIS_ADMIN(String is_admin) {

        this.is_admin = is_admin;
    }
    public String getATASAN_NAME() { return atasan_name; }

    public void setATASAN_NAME(String atasan_name) {

        this.atasan_name = atasan_name;
    }


}
