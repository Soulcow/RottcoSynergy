package synergy.rottco.bullet.black.rottcosynergy;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class Dialogs {

	private static Dialogs instance;
	private Dialogs(){}

	public static synchronized Dialogs getInstance()
	{

		if (instance == null) {
			instance = new Dialogs();
		}
		return instance;

	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	public void showAlertDialog(Context context,CharSequence title,CharSequence message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.create().show();
	}
	public void showAlertDialog(Context context,CharSequence title,CharSequence message,DialogInterface.OnDismissListener dismissListener)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setOnDismissListener(dismissListener);
		builder.create().show();
	}

	public void showAlertDialog(Context context,CharSequence title,CharSequence message,DialogInterface.OnDismissListener dismissListener,String positiveButtonName,DialogInterface.OnClickListener positiveListener,String negativeButtonName,DialogInterface.OnClickListener negativeListnere)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setOnDismissListener(dismissListener);
		builder.setPositiveButton(positiveButtonName, positiveListener);
		builder.setNegativeButton(negativeButtonName, negativeListnere);
		builder.create().show();
	}

	public void showAlertDialog(Context context,CharSequence title,CharSequence message,String positiveButtonName,DialogInterface.OnClickListener positiveListener)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(positiveButtonName, positiveListener);
		builder.create().show();
	}
}



//class SingletonClass {
//
//	private static SingletonClass singletonObject;
//	/** A private Constructor prevents any other class from instantiating. */
//	private SingletonClass() {
//		//	 Optional Code
//	}
//	public static synchronized SingletonClass getSingletonObject() {
//		if (singletonObject == null) {
//			singletonObject = new SingletonClass();
//		}
//		return singletonObject;
//	}
//	public Object clone() throws CloneNotSupportedException {
//		throw new CloneNotSupportedException();
//	}
//}
//
//public class SingletonObjectDemo {
//
//	public static void main(String args[]) {
//		//		SingletonClass obj = new SingletonClass();
//		//Compilation error not allowed
//		SingletonClass obj = SingletonClass.getSingletonObject();
//		// Your Business Logic
//		System.out.println("Singleton object obtained");
//	}
//}