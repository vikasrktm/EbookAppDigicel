package com.digicelplaypngezine;

//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.digicelplaypngezine.R;


public class MainActivity extends FragmentActivity {
   LayoutInflater mInflater;
    TouchImageView  mImageView;
   static int[] images = {

           R.drawable.p01,
           R.drawable.p02,
           R.drawable.p03,
           R.drawable.p04,
           R.drawable.p05,
           R.drawable.p06,
           R.drawable.p07,
           R.drawable.p08,
           R.drawable.p09,
           R.drawable.p10,
           R.drawable.p11,
           R.drawable.p12,
           R.drawable.p13,
           R.drawable.p14,
           R.drawable.p15,
           R.drawable.p16,
           R.drawable.p17,
           R.drawable.p18,
           R.drawable.p19,
           R.drawable.p20,
           R.drawable.p21,
           R.drawable.p22,
           R.drawable.p23,
           R.drawable.p24,
           R.drawable.p25,
           R.drawable.p26,
           R.drawable.p27,
           R.drawable.p28,
           R.drawable.p29,
           R.drawable.p30,
           R.drawable.p31,
           R.drawable.p32,
           R.drawable.p33,
           R.drawable.p34,
           R.drawable.p35,
           R.drawable.p36,
           R.drawable.p37,
           R.drawable.p38,
           R.drawable.p39,
           R.drawable.p40,
           R.drawable.p41,
           R.drawable.p42,
           R.drawable.p43,
           R.drawable.p44,
           R.drawable.p45,
           R.drawable.p46,
           R.drawable.p47,
           R.drawable.p48,


  };
    MyViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mViewPager = (MyViewPager) findViewById(R.id.pager);
//        mImageView = new ImageView(this);
        mViewPager.setAdapter(new MyAdapter());



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
//pageradapter
//   {
        //PagerAdapter

    private  class  MyAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position){

//           Toast.makeText(MainActivity.this," Instantiate item "+ position,Toast.LENGTH_SHORT).show();;

          position = position%images.length;
         mImageView = new TouchImageView(MainActivity.this, mViewPager);

           try {
               mImageView.setImageResource(images[position]);

           }
           catch (OutOfMemoryError e)
           {
//               Toast.makeText(MainActivity.this," out of memory",Toast.LENGTH_SHORT).show();

           }

           ViewGroup.LayoutParams mLayoutParams = new ViewGroup.LayoutParams
           (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mImageView.setLayoutParams(mLayoutParams);
          container.addView(mImageView);
            return mImageView;
        }

        @Override
        public int getCount() {
            Log.d("in get count","erynrnh" );
            return images.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

//       Toast.makeText(MainActivity.this,"destry "+ position,Toast.LENGTH_SHORT).show();
            position = position;
            container.removeView((View)object);
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view==o ;
        }
    }
//PagerAdapter
//    }


    //fragmentstatepageradapter



//    private class MyAdapter extends FragmentStatePagerAdapter {
//
//        public MyAdapter(FragmentManager fm) {
//            super(fm);
//          Toast.makeText(MainActivity.this,"my adapter contructer ",Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public int getCount() {
//
////            Toast.makeText(MainActivity.this,"getcounbt ",Toast.LENGTH_SHORT).show();
//            return images.length;
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            Toast.makeText(MainActivity.this,"get item  pos = "+ position,Toast.LENGTH_SHORT).show();
//            return MyFragment.newInstance(position);
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            Toast.makeText(MainActivity.this,"mdestry item pos = "+ position,Toast.LENGTH_SHORT).show();
//            super.destroyItem(container, position, object);
////            ((ViewGroup) container).removeView((View) object);
//        }
//    }

}
