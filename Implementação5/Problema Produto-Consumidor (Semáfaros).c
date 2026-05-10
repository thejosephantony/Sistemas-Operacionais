#include <stdbool.h>
#include <unistd.h>
#include <stdio.h>

#define N 100                 /* número de lugares no buffer */

typedef int semaphore;        /* semáforo é um tipo especial de inteiro */

semaphore mutex = 1;          /* controla acesso à região crítica */
semaphore empty = N;          /* conta lugares vazios no buffer */
semaphore full = 0;           /* conta lugares preenchidos no buffer */

void producer(void) {
    int item;

    while (true) {
        item = produce_item();    /* gera algo para pôr no buffer */

        down(&empty);             /* decrementa espaços vazios */
        down(&mutex);             /* entra na região crítica */

        insert_item(item);        /* coloca item no buffer */

        up(&mutex);               /* sai da região crítica */
        up(&full);                /* incrementa lugares preenchidos */
    }
}

void consumer(void) {
    int item;

    while (true) {
        down(&full);              /* decrementa lugares preenchidos */
        down(&mutex);             /* entra na região crítica */

        item = remove_item();     /* retira item do buffer */

        up(&mutex);               /* sai da região crítica */
        up(&empty);               /* incrementa lugares vazios */

        consume_item(item);       /* faz algo com o item */
    }
}