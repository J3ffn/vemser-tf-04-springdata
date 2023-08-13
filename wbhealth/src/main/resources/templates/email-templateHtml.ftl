<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TESTE</title>
</head>

<body>
<div style="display: grid; width: 40%; margin: 0px; padding: 0px;">

    <div style="display: block; background-color: rgba(217, 217, 217, 0.2); margin-top: 10px; margin: 40px; border-radius: 16px; padding: 10px 0 40px 0px;">

        <div style="text-align: center; margin: 20px 0px 20px 0px;">
            <img src="${titulo}" alt="Logo WBHealth">
        </div>

        <div style="margin-left: 40px;">
            <p>Olá, ${nome}</p>
            <p>Seu atendimento foi confirmado com sucesso!</p>
            <p>Informações sobre o atendimento:</p>
        </div>

        <div style="border-radius: 17px; background-color: rgba(217, 217, 217, 0.53); margin: 10px 3% 10px 3%; padding: 10px 0px 10px 0px;">
            <ul style="list-style: none; padding: 0px; margin-left: 35px; padding: 10px 0px 10px 0px;">
                <li>Tipo: teste1</li>
                <li>Data: teste2</li>
                <li>Laudo: teste3</li>
                <li>Medico: teste4</li>
                <li>Valor: teste5</li>
            </ul>
        </div>

        <div style="display: grid; margin: 0px 3% 0px 3%; margin-top: 0px; grid-template-columns: 1fr 1fr; border-radius: 17px;">
            <div style="text-align: start; margin: 0; padding: 10px; grid-column: 1; border-radius: 17px 0px 0px 17px;">
                <p style="display: inline-block; font-size: 20px;">Dúvidas? Entre em contato conosco: <br>${emailSuporte}</p>
            </div>
            <div style="text-align: end; grid-column: 2; border-radius: 0px 17px 17px 0px;">
                <a href="" style="color: #fff; display: inline-block; background-color: #F73C5C; margin-top: 10px; margin-right: 10px; border-radius: 11px; padding: 15px; text-decoration: none;">Cancelar atendimento</a>
            </div>
        </div>

    </div>

    <div style="display: grid; grid-template-columns: 1fr 1fr; background-color: rgba(217, 217, 217, 0.2); border-radius: 16px 16px 0px 0px; margin: 0 40px 0 40px; padding: 20px 20px 20px 20px;">
        <div style="display: inline; grid-column: 1;">
            <div style="display: inline;">
                <img src="${facebook}" alt="Facebook"  style="">
                <p style="display: inline;">facebook</p>
            </div>
            <div style="display: inline;">
                <img src="${instagram}" alt="Instagram">
                <p style="display: inline;">instagram</p>
            </div>
        </div>
        <div style="display: inline-block; grid-column: 2; justify-self: end;">
            <img src="${icone}" alt="icone">
        </div>
    </div>

</div>

</body>

</html>
