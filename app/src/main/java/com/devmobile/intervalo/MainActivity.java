package com.devmobile.intervalo;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import com.devmobile.intervalo.model.Post;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private RecyclerView recyclerPosts;
    private PostAdapter adapter;
    private final List<Post> postList = new ArrayList<>();

    // Nome/curso "fixos" simulando o usuário logado.
    // Numa versão futura isso viria do perfil do usuário autenticado.
    private static final String CURRENT_USER_NAME = "Kauan Oliveira";
    private static final String CURRENT_USER_COURSE = "Ciência da Computação";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Autenticação simples (RF01): entra como usuário anônimo automaticamente.
        if (auth.getCurrentUser() == null) {
            auth.signInAnonymously().addOnFailureListener(e ->
                    Toast.makeText(this, "Falha ao autenticar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }

        recyclerPosts = findViewById(R.id.recyclerPosts);
        recyclerPosts.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PostAdapter(postList, new PostAdapter.OnPostActionListener() {
            @Override
            public void onEdit(Post post) {
                showPostDialog(post);
            }

            @Override
            public void onDelete(Post post) {
                deletePost(post);
            }
        });
        recyclerPosts.setAdapter(adapter);

        findViewById(R.id.btnAddPost).setOnClickListener(v -> showPostDialog(null));

        listenToPosts();
    }

    // READ — escuta em tempo real a coleção "posts", ordenada da mais recente pra mais antiga
    private void listenToPosts() {
        db.collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((snapshots, error) -> {
                    if (error != null) {
                        Toast.makeText(this, "Erro ao carregar posts: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (snapshots == null) return;

                    postList.clear();
                    snapshots.forEach(doc -> {
                        Post post = doc.toObject(Post.class);
                        post.setId(doc.getId());
                        postList.add(post);
                    });
                    adapter.notifyDataSetChanged();
                });
    }

    // CREATE / UPDATE
    private void showPostDialog(Post existingPost) {
        View view = getLayoutInflater().inflate(R.layout.dialog_post, null);
        EditText etText = view.findViewById(R.id.etPostText);

        boolean isEditing = existingPost != null;
        if (isEditing) {
            etText.setText(existingPost.getText());
        }

        new AlertDialog.Builder(this)
                .setTitle(isEditing ? "Editar publicação" : "Nova publicação")
                .setView(view)
                .setPositiveButton(isEditing ? "Salvar" : "Publicar", (dialog, which) -> {
                    String text = etText.getText().toString().trim();
                    if (text.isEmpty()) {
                        Toast.makeText(this, "Escreva algo antes de publicar", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (isEditing) {
                        updatePost(existingPost, text);
                    } else {
                        createPost(text);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void createPost(String text) {
        Post post = new Post(CURRENT_USER_NAME, CURRENT_USER_COURSE, text, System.currentTimeMillis());
        db.collection("posts")
                .add(post)
                .addOnSuccessListener(ref -> Toast.makeText(this, "Publicado!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Erro ao publicar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void updatePost(Post post, String newText) {
        db.collection("posts")
                .document(post.getId())
                .update("text", newText)
                .addOnSuccessListener(unused -> Toast.makeText(this, "Publicação atualizada", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Erro ao atualizar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // DELETE
    private void deletePost(Post post) {
        new AlertDialog.Builder(this)
                .setTitle("Excluir publicação")
                .setMessage("Tem certeza que deseja excluir esta publicação?")
                .setPositiveButton("Excluir", (dialog, which) ->
                        db.collection("posts")
                                .document(post.getId())
                                .delete()
                                .addOnFailureListener(e -> Toast.makeText(this, "Erro ao excluir: " + e.getMessage(), Toast.LENGTH_SHORT).show()))
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
