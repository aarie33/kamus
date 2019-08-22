package arie.kamus.db;

import android.os.Parcel;
import android.os.Parcelable;

public class EnglishModel implements Parcelable {
    private int id;
    private String kata, keterangan;

    public EnglishModel(){

    }

    public EnglishModel(String kata, String keterangan) {
        this.kata = kata;
        this.keterangan = keterangan;
    }

    public EnglishModel(int id, String kata, String keterangan) {
        this.id = id;
        this.kata = kata;
        this.keterangan = keterangan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(this.id);
        dest.writeString(this.kata);
        dest.writeString(this.keterangan);
    }

    protected EnglishModel(Parcel in) {
        this.id = in.readInt();
        this.kata = in.readString();
        this.keterangan = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKata() {
        return kata;
    }

    public void setKata(String kata) {
        this.kata = kata;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public static final Parcelable.Creator<EnglishModel> CREATOR = new Creator<EnglishModel>() {
        @Override
        public EnglishModel createFromParcel(Parcel in) {
            return new EnglishModel(in);
        }

        @Override
        public EnglishModel[] newArray(int size) {
            return new EnglishModel[size];
        }
    };
}
