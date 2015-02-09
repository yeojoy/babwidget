package me.yeojoy.bab.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Set;

/**
 * Created by yeojoy on 15. 2. 5..
 */
public class TodayMenu implements Parcelable {
    
    private String date;
    private String mainMenu;

    private String subMenuFirst;
    private String subMenuSecond;
    private String subMenuThird;
    private String subMenuFourth;
    
    private Set<String> otherMenus;
    private Set<String> dieteticInfo;

    public Set<String> getOtherMenus() {
        return otherMenus;
    }

    public void setOtherMenus(Set<String> otherMenus) {
        this.otherMenus = otherMenus;
    }

    public Set<String> getDieteticInfo() {
        return dieteticInfo;
    }

    public void setDieteticInfo(Set<String> dieteticInfo) {
        this.dieteticInfo = dieteticInfo;
    }

    public TodayMenu() { }

    public TodayMenu(String date, String mainMenu, String subMenuFirst, String subMenuSecond, String subMenuThird, String subMenuFourth) {
        this.date = date;
        this.mainMenu = mainMenu;
        this.subMenuFirst = subMenuFirst;
        this.subMenuSecond = subMenuSecond;
        this.subMenuThird = subMenuThird;
        this.subMenuFourth = subMenuFourth;
    }

    public TodayMenu(String date, String mainMenu, String subMenuFirst, String subMenuSecond, String subMenuThird, String subMenuFourth, Set<String> otherMenus, Set<String> dieteticInfo) {
        this.date = date;
        this.mainMenu = mainMenu;
        this.subMenuFirst = subMenuFirst;
        this.subMenuSecond = subMenuSecond;
        this.subMenuThird = subMenuThird;
        this.subMenuFourth = subMenuFourth;
        this.otherMenus = otherMenus;
        this.dieteticInfo = dieteticInfo;
    }

    public TodayMenu(Parcel in) {
        date = in.readString();
        mainMenu = in.readString();
        subMenuFirst = in.readString();
        subMenuSecond = in.readString();
        subMenuThird = in.readString();
        subMenuFourth = in.readString();
    }

    @Override
    public String toString() {
        return "TodayMenu{" +
                "date='" + date + '\'' +
                ", mainMenu='" + mainMenu + '\'' +
                ", subMenuFirst='" + subMenuFirst + '\'' +
                ", subMenuSecond='" + subMenuSecond + '\'' +
                ", subMenuThird='" + subMenuThird + '\'' +
                ", subMenuFourth='" + subMenuFourth + '\'' +
                ", otherMenus=" + otherMenus +
                ", dieteticInfo=" + dieteticInfo +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(mainMenu);
        dest.writeString(subMenuFirst);
        dest.writeString(subMenuSecond);
        dest.writeString(subMenuThird);
        dest.writeString(subMenuFourth);
    }
    
    public static final Creator CREATOR = new Creator() {
        @Override
        public TodayMenu createFromParcel(Parcel source) {
            return new TodayMenu(source);
        }

        @Override
        public TodayMenu[] newArray(int size) {
            return new TodayMenu[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMainMenu() {
        return mainMenu;
    }

    public void setMainMenu(String mainMenu) {
        this.mainMenu = mainMenu;
    }

    public String getSubMenuFirst() {
        return subMenuFirst;
    }

    public void setSubMenuFirst(String subMenuFirst) {
        this.subMenuFirst = subMenuFirst;
    }

    public String getSubMenuSecond() {
        return subMenuSecond;
    }

    public void setSubMenuSecond(String subMenuSecond) {
        this.subMenuSecond = subMenuSecond;
    }

    public String getSubMenuThird() {
        return subMenuThird;
    }

    public void setSubMenuThird(String subMenuThird) {
        this.subMenuThird = subMenuThird;
    }

    public String getSubMenuFourth() {
        return subMenuFourth;
    }

    public void setSubMenuFourth(String subMenuFourth) {
        this.subMenuFourth = subMenuFourth;
    }
}
