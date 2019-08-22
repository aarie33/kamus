package arie.kamus.db;

import android.os.Parcel;
import android.os.Parcelable;

public class IndonesiaModel implements Parcelable {
    private int id;
    private String kata, keterangan;

    public IndonesiaModel(){

    }

    public IndonesiaModel(String kata, String keterangan) {
        this.kata = kata;
        this.keterangan = keterangan;
    }

    public IndonesiaModel(int id, String kata, String keterangan) {
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

    protected IndonesiaModel(Parcel in) {
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

    public static final Parcelable.Creator<IndonesiaModel> CREATOR = new Creator<IndonesiaModel>() {
        @Override
        public IndonesiaModel createFromParcel(Parcel in) {
            return new IndonesiaModel(in);
        }

        @Override
        public IndonesiaModel[] newArray(int size) {
            return new IndonesiaModel[size];
        }
    };
}
