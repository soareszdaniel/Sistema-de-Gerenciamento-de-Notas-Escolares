document.getElementById('formCadastro').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const nome = document.getElementById('nome').value;
    const matricula = document.getElementById('matricula').value;
    const turma = document.getElementById('turma').value;

    fetch('http://localhost:8081/api/alunos', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `nome=${encodeURIComponent(nome)}&matricula=${matricula}&turma=${turma}`
    })
    .then(response => response.text())
    .then(data => {
        alert(data);
        window.location.href = 'index.html';
    })
    .catch(error => console.error('Erro:', error));
});