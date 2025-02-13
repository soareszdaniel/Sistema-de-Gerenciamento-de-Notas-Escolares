document.getElementById('formCadastro').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const dados = {
        nome: document.getElementById('nome').value,
        matricula: document.getElementById('matricula').value,
        turma: document.getElementById('turma').value
    };

    try {
        const resposta = await fetch('http://localhost:8081/api/alunos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams(dados)
        });
        
        const mensagem = await resposta.text();
        alert(mensagem);
        window.location.href = 'index.html';
    } catch (erro) {
        console.error('Erro:', erro);
        alert('Falha ao cadastrar!');
    }
});