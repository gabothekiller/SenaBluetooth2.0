package app.akexorcist.bluetoothspp;

import android.os.Parcel;
import android.os.Parcelable;

public class GraphDataPackage implements Parcelable {
    int sensor1;
    int sensor2;
    int sensor3;
    String date;
    // hello github

    public GraphDataPackage(int sensor1, String date){
        this.sensor1 = sensor1;
        this.date = date;
    }

    public GraphDataPackage(int sensor1, int sensor2, String date){
        this.sensor1 = sensor1;
        this.sensor2 = sensor2;
        this.date = date;
    }
    public GraphDataPackage(int sensor1, int sensor2, int sensor3, String date){
        this.sensor1 = sensor1;
        this.sensor2 = sensor2;
        this.sensor3 = sensor3;
        this.date = date;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(sensor1);
        out.writeInt(sensor2);
        out.writeString(date);
    }

    public static final Parcelable.Creator<GraphDataPackage> CREATOR
            = new Parcelable.Creator<GraphDataPackage>() {
        public GraphDataPackage createFromParcel(Parcel in) {
            return new GraphDataPackage(in);
        }

        public GraphDataPackage[] newArray(int size) {
            return new GraphDataPackage[size];
        }
    };

    private GraphDataPackage(Parcel in) {
        sensor1 = in.readInt();
        sensor2 = in.readInt();
        date = in.readString();
    }
}
