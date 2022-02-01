package com.gr.xxx.smartvision.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lens")
public class Lens implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    String time;
    String date;
    String typeString;
    int type;
    String cylString;
    int cylPosition;
    String sphString;
    int sphPosition;
    int quantity;
    double price;
    String priceString;

    public Lens(String time,
                String date,
                String typeString,
                int type,
                String cylString,
                int cylPosition,
                String sphString,
                int sphPosition,
                int quantity,
                double price,
                String priceString) {

        this.time = time;
        this.date = date;
        this.typeString = typeString;
        this.type = type;
        this.cylString = cylString;
        this.cylPosition = cylPosition;
        this.sphString = sphString;
        this.sphPosition = sphPosition;
        this.quantity = quantity;
        this.price = price;
        this.priceString = priceString;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeString() {
        return typeString;
    }

    public int getCylPosition() {
        return cylPosition;
    }

    public void setCylPosition(int cylPosition) {
        this.cylPosition = cylPosition;
    }

    public int getSphPosition() {
        return sphPosition;
    }

    public void setSphPosition(int sphPosition) {
        this.sphPosition = sphPosition;
    }

    public void setTypeString(String typeString) {
        this.typeString = typeString;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCylString() {
        return cylString;
    }

    public void setCylString(String cylString) {
        this.cylString = cylString;
    }

    public String getSphString() {
        return sphString;
    }

    public void setSphString(String sphString) {
        this.sphString = sphString;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    protected Lens(Parcel in) {
        time = in.readString();
        date = in.readString();
        typeString = in.readString();
        type = in.readInt();
        cylString = in.readString();
        cylPosition = in.readInt();
        sphString = in.readString();
        sphPosition = in.readInt();
        quantity = in.readInt();
        price = in.readDouble();
        priceString = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(time);
        dest.writeString(date);
        dest.writeString(typeString);
        dest.writeInt(type);
        dest.writeString(cylString);
        dest.writeInt(cylPosition);
        dest.writeString(sphString);
        dest.writeInt(sphPosition);
        dest.writeInt(quantity);
        dest.writeDouble(price);
        dest.writeString(priceString);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Lens> CREATOR = new Parcelable.Creator<Lens>() {
        @Override
        public Lens createFromParcel(Parcel in) {
            return new Lens(in);
        }

        @Override
        public Lens[] newArray(int size) {
            return new Lens[size];
        }
    };
}