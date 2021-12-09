package OperacionesPedido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.gestorinventario.R;
import com.google.android.material.tabs.TabLayout;

public class HistorialPedidos extends AppCompatActivity{

    private TabLayout tablayout;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pedidos);

        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);

        tablayout.addTab(tablayout.newTab().setText("Pedidos enviados"));
        tablayout.addTab(tablayout.newTab().setText("Pedidos pendientes"));

        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {

                switch (position){
                    case 0:
                        EnviadosFragment enviadosFragment = new EnviadosFragment();
                        return enviadosFragment;

                    case 1:
                        PendienteFragment pendienteFragment = new PendienteFragment();
                        return pendienteFragment;

                    default:
                        return  null;
                }
            }

            @Override
            public int getCount() {
                return tablayout.getTabCount();
            }
        });


        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }
}