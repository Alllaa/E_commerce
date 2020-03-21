package com.example.e_commerce.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Address implements Parcelable {

    @SerializedName("id")
    private String id;
    @SerializedName("city")
    private String city;
    @SerializedName("street")
    private String atreet;
    @SerializedName("building")
    private String building;
    @SerializedName("floor")
    private String floor;
    @SerializedName("apartment")
    private String apartment;
    @SerializedName("landmark")
    private String landmark;
    @SerializedName("phone")
    private String phone;
    @SerializedName("notes")
    private String notes;
    @SerializedName("zip")
    private String zip;

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Address(String city, String atreet, String building, String floor, String apartment, String landmark, String phone, String notes) {
        this.city = city;
        this.atreet = atreet;
        this.building = building;
        this.floor = floor;
        this.apartment = apartment;
        this.landmark = landmark;
        this.phone = phone;
        this.notes = notes;
    }

    protected Address(Parcel in) {
        id = in.readString();
        city = in.readString();
        atreet = in.readString();
        building = in.readString();
        floor = in.readString();
        apartment = in.readString();
        landmark = in.readString();
        phone = in.readString();
        notes = in.readString();
        zip = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAtreet() {
        return atreet;
    }

    public void setAtreet(String atreet) {
        this.atreet = atreet;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(city);
        parcel.writeString(atreet);
        parcel.writeString(building);
        parcel.writeString(floor);
        parcel.writeString(apartment);
        parcel.writeString(landmark);
        parcel.writeString(phone);
        parcel.writeString(notes);
        parcel.writeString(zip);
    }
}
