package sg.edu.np.madpractical;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class DBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userDB.db";
    public static  final String USER = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_FOLLOW = "followed";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context,DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public DBHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public  void onCreate(SQLiteDatabase db)
    {
        ArrayList<User> userArrayList = new ArrayList<User>();
        for (int i = 0; i < 20; i ++)
        {
            User user = new User("name"+ranNum(), "description"+ranNum(), ranFollow());
            userArrayList.add(user);
        }
        String CREATE_USER_TABLE = "CREATE TABLE " + USER + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
                + COLUMN_DESC + " TEXT," + COLUMN_FOLLOW + " INTEGER" + ")";

        db.execSQL(CREATE_USER_TABLE);
        for (User i: userArrayList)
        {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, i.getName());
            values.put(COLUMN_DESC, i.getDescription());
            values.put(COLUMN_FOLLOW, i.getFollowed());

            db.insert(USER,null,values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + USER);
        onCreate(db);
    }

    public void addUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESC, user.getDescription());
        values.put(COLUMN_FOLLOW, user.getFollowed());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(USER,null ,values);
        db.close();
    }

    public ArrayList<User> getUsers()
    {
        ArrayList<User> userArrayList = new ArrayList<User>();
        String query = "SELECT * FROM " + USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst())
        {
            do {
                User user = new User();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setDescription(cursor.getString(2));
                if(cursor.getString(3).equals(1))
                {
                    user.setFollowed(true);
                }
                else
                {
                    user.setFollowed(false);
                }
                userArrayList.add(user);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return userArrayList;
    }

    public void updateUser(User user)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOLLOW, user.getFollowed());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(USER, values, COLUMN_ID + " = ?", (new String[] {String.valueOf(user.getId())}));
        db.close();
    }

    public int ranNum()
    {
        Random random = new Random();
        return random.nextInt();
    }

    public boolean ranFollow()
    {
        Random random = new Random();
        if (random.nextInt(1) == 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
