// curl https://api.yookassa.ru/v3/payments \
//     -X POST \
//   -u <Идентификатор магазина>:<Секретный ключ> \
//     -H 'Idempotence-Key: <Ключ идемпотентности>' \
//         -H 'Content-Type: application/json' \
//         -d '{
//             "amount": {
//             "value": "100.00",
//             "currency": "RUB"
//         },
//             "capture": false,
//             "confirmation": {
//             "type": "redirect",
//             "return_url": "https://www.example.com/return_url"
//         },
//             "description": "Заказ №37",
//             "metadata": {
//             "order_id": "37"
//         }
//         }'


