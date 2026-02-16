package fr.iutblagnac.devmobtp1p2;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.view.MenuInflater;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fr.iutblagnac.devmobtp1p2.model.Todo;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Todo> tlAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        tlAdapter = new ArrayAdapter<Todo>(this, android.R.layout.simple_list_item_1, this.createListTodoTest());
        
        listView = findViewById(R.id.lv_id);
        listView.setAdapter(tlAdapter);
        registerForContextMenu(listView);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private List<Todo> createListTodoTest() {
        ArrayList<Todo> testListTodo;
        testListTodo = new ArrayList<>();
        testListTodo.add(new Todo("devoir 1",
                "Deposer le TP1 de CDAM deja en retard"));
        testListTodo.add(new Todo("devoir 2",
                "Deposer le TP2 de CDAM deja en retard"));
        testListTodo.add(new Todo("devoir 3",
                "Deposer le TP3 de CDAM, il est encore temps"));
        testListTodo.add(new Todo("Courses",
                "Du lait, du beurre et des epinards"));
        testListTodo.add(new Todo("Fete",
                "Organiser jeudi soir !! avant jeudi soir !!"));
        testListTodo.add(new Todo("devoir 4",
                "Deposer le TP4, il est encore temps", "My colleague", "", 23, 3, 2023));
        testListTodo.add(new Todo("PJT", "Preparer rendu pjt",
                "My colleagues", "Ya du taf a faire\nFaut pas trainer",
                24, 3, 2023));
        for (int i = 0; i < 10; i++) {
            testListTodo.add(new Todo("Titre " + i, "Todo " + i));
        }
        return testListTodo;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_todo_menu, menu);
        menu.setHeaderTitle("Choisir :");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Todo selectedTodo = tlAdapter.getItem(info.position);
        int pos = info.position;

        if (item.getItemId() == R.id.listview_menu_show_todo_id) {
            Toast.makeText(this, "Afficher todo #" + pos + ": " + selectedTodo.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.listview_menu_modify_todo_id) {
            Toast.makeText(this, "Modifier todo #" + pos + ": " + selectedTodo.getTitle(), Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.listview_menu_delete_todo_id) {
            showDeleteConfirmationDialog(selectedTodo, pos);
            return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_menu_ajouter_todo_id) {
            Toast.makeText(this, "Ajouter un todo", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDeleteConfirmationDialog(final Todo todo, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Supprimer vraiment ?\n" + todo.getTitle() + " : " + todo.getTodo())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteTodo(todo, pos);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteTodo(Todo todo, int pos) {
        tlAdapter.remove(todo);
        tlAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Supprimé todo #" + pos + ": " + todo.getTitle(), Toast.LENGTH_SHORT).show();
    }
}