#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define NUMBER_OF_THREADS 10

void *print_hello_world(void *tid) {
    long id = (long) tid;

    printf("Olá mundo. Boas-vindas da thread %ld\n", id);

    return NULL;
}

int main() {
    pthread_t threads[NUMBER_OF_THREADS];
    int status;
    long i;

    for (i = 0; i < NUMBER_OF_THREADS; i++) {
        printf("Main: criando thread %ld\n", i);

        status = pthread_create(&threads[i], NULL, print_hello_world, (void *) i);

        if (status != 0) {
            printf("Erro ao criar thread. Código: %d\n", status);
            exit(-1);
        }
    }

    for (i = 0; i < NUMBER_OF_THREADS; i++) {
        pthread_join(threads[i], NULL);
    }

    printf("Todas as threads terminaram.\n");

    return 0;
}
