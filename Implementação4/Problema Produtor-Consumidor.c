#include <stdbool.h>
#include <unistd.h>
#include <stdio.h>


#define N 100              /* número de posições no buffer */

int count = 0;             /* número de itens no buffer */

void producer(void) {
    int item;

    while (true) {                         /* repita para sempre */
        item = produce_item();             /* gera o próximo item */

        if (count == N) {
            sleep();                       /* se o buffer estiver cheio, dorme */
        }

        insert_item(item);                 /* coloca item no buffer */
        count = count + 1;                 /* incrementa contador */

        if (count == 1) {
            wakeup(consumer);              /* se estava vazio, acorda consumidor */
        }
    }
}

void consumer(void) {
    int item;

    while (true) {                         /* repita para sempre */
        if (count == 0) {
            sleep();                       /* se o buffer estiver vazio, dorme */
        }

        item = remove_item();              /* retira item do buffer */
        count = count - 1;                 /* decrementa contador */

        if (count == N - 1) {
            wakeup(producer);              /* se estava cheio, acorda produtor */
        }

        consume_item(item);                /* consome o item */
    }
}