package br.com.bossini.usjt_ccp3anbua_livros_rest_json_basic_activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView livrosRecyclerView;
    private LivroAdapter adapter;
    private List <Livro> livros;
    private SwipeRefreshLayout swipeRefreshLayout;

    private void setupRecyleView (){
        livrosRecyclerView = findViewById(R.id.livrosRecyclerView);
        livros = new ArrayList<>();
        adapter = new LivroAdapter(this, livros);
        livrosRecyclerView.setAdapter(adapter);
        livrosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupSwipeRefreshLayout (){
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            new ListarLivrosTask().execute(
                    montaURL(
                            getString(R.string.host_address),
                            getString(R.string.host_port),
                            getString(R.string.endpoint_base),
                            getString(R.string.endpoint_listar)
                    )
            );
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupRecyleView();
        setupSwipeRefreshLayout();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public String montaURL (String... arguments){
        StringBuilder sb = new StringBuilder("");
        for (String arg : arguments){
            sb.append(arg);
        }
        return sb.toString();
    }

    private class ListarLivrosTask extends AsyncTask <String, Void, JSONArray>{

        @Override
        protected JSONArray doInBackground(String... strings) {
           String endereco = strings[0];
           try{
               URL url = new URL (endereco);
               HttpURLConnection conn = (HttpURLConnection)
                       url.openConnection();
               try (BufferedReader reader =
                       new BufferedReader(new InputStreamReader(conn.getInputStream()))){
                  StringBuilder sb = new StringBuilder ("");
                  String linha = null;
                  while ((linha = reader.readLine()) != null){
                      sb.append(linha);
                  }
                  return new JSONArray(sb.toString());
               }

           }
           catch (Exception e){
               e.printStackTrace();
               return null;
           }
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++){
                try {
                    JSONObject iesimo = jsonArray.getJSONObject(i);
                    int id = iesimo.getInt("id");
                    String titulo = iesimo.getString("titulo");
                    String autor = iesimo.getString("autor");
                    String edicao = iesimo.getString("edicao");
                    int numeroPaginas = iesimo.getInt("numeroPaginas");
                    Livro l = new Livro (titulo, autor, edicao, numeroPaginas);
                    livros.add(l);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            adapter.notifyDataSetChanged();
        }
    }
}

class LivroViewHolder extends RecyclerView.ViewHolder {

    public LivroViewHolder (View raiz){
        super (raiz);
        this.tituloTextView =
                raiz.findViewById(R.id.tituloTextView);
        this.autorTextView =
                raiz.findViewById(R.id.autorTextView);
        this.edicaoTextView =
                raiz.findViewById(R.id.edicaoTextView);

    }

    public TextView tituloTextView;
    public TextView autorTextView;
    public TextView edicaoTextView;

}

class LivroAdapter extends RecyclerView.Adapter <LivroViewHolder>{

    private Context context;
    private List <Livro> livros;

    public LivroAdapter(Context context, List<Livro> livros) {
        this.context = context;
        this.livros = livros;
    }

    @NonNull
    @Override
    public LivroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View raiz = inflater.inflate(
                R.layout.list_item,
                parent,
                false
        );
        return new LivroViewHolder(raiz);
    }

    @Override
    public void onBindViewHolder(@NonNull LivroViewHolder holder, int position) {
        Livro livro = livros.get(position);
        holder.tituloTextView.setText(livro.getTitulo());
        holder.autorTextView.setText(livro.getAutor());
        holder.edicaoTextView.setText(livro.getEdicao());
    }

    @Override
    public int getItemCount() {
        return livros.size();
    }
}
