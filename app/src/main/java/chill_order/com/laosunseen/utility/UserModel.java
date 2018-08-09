package chill_order.com.laosunseen.utility;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Constructor;

public class UserModel implements Parcelable {

    private String uidString, nameString, emailString, pathURLString, myPostString;

    public UserModel() {


    } // Constructor Getter

    public UserModel(String uidString, String nameString, String emailString, String pathURLString, String myPostString) {
        this.uidString = uidString;
        this.nameString = nameString;
        this.emailString = emailString;
        this.pathURLString = pathURLString;
        this.myPostString = myPostString;
    } // Constructor Setter

    protected UserModel(Parcel in) {
        uidString = in.readString();
        nameString = in.readString();
        emailString = in.readString();
        pathURLString = in.readString();
        myPostString = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getUidString() {
        return uidString;
    }

    public void setUidString(String uidString) {
        this.uidString = uidString;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getEmailString() {
        return emailString;
    }

    public void setEmailString(String emailString) {
        this.emailString = emailString;
    }

    public String getPathURLString() {
        return pathURLString;
    }

    public void setPathURLString(String pathURLString) {
        this.pathURLString = pathURLString;
    }

    public String getMyPostString() {
        return myPostString;
    }

    public void setMyPostString(String myPostString) {
        this.myPostString = myPostString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uidString);
        parcel.writeString(nameString);
        parcel.writeString(emailString);
        parcel.writeString(pathURLString);
        parcel.writeString(myPostString);
    }
}   // Main Class
