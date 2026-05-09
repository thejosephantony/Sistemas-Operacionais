#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>

#define NUMBER_OF_THREADS 10

/* Esta função imprime o identificador da thread e sai. */
void *print_hello_world(void *tid) {
    long id = (long) tid;

    printf("Olá mundo. Boas-vindas da thread %ld\n", id);

    pthread_exit(NULL);
}

int main(int argc, char *argv[]) {
    pthread_t threads[NUMBER_OF_THREADS];
    int status;
    long i;

    printf("Método main. Criando threads.\n");

    for (i = 0; i < NUMBER_OF_THREADS; i++) {
        printf("Main: criando thread %ld\n", i);

        status = pthread_create(
            &threads[i],
            NULL,
            print_hello_world,
            (void *) i
        );

        if (status != 0) {
            printf("Erro: pthread_create retornou código %d\n", status);
            exit(-1);
        }
    }

    pthread_exit(NULL);
}
