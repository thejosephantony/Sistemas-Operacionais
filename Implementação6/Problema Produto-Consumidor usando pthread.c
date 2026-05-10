#include <stdio.h>
#include <pthread.h>

#define MAX 10

pthread_mutex_t the_mutex;
pthread_cond_t condc, condp;

int buffer = 0; 
/* buffer compartilhado entre produtor e consumidor
   buffer == 0 significa vazio
   buffer != 0 significa cheio */

void *producer(void *ptr) {
    int i;

    for (i = 1; i <= MAX; i++) {
        pthread_mutex_lock(&the_mutex);

        while (buffer != 0) {
            pthread_cond_wait(&condp, &the_mutex);
        }

        buffer = i;
        printf("Produtor produziu: %d\n", buffer);

        pthread_cond_signal(&condc);

        pthread_mutex_unlock(&the_mutex);
    }

    pthread_exit(NULL);
}

void *consumer(void *ptr) {
    int i;

    for (i = 1; i <= MAX; i++) {
        pthread_mutex_lock(&the_mutex);

        while (buffer == 0) {
            pthread_cond_wait(&condc, &the_mutex);
        }

        printf("Consumidor consumiu: %d\n", buffer);
        buffer = 0;

        pthread_cond_signal(&condp);

        pthread_mutex_unlock(&the_mutex);
    }

    pthread_exit(NULL);
}

int main(int argc, char **argv) {
    pthread_t pro, con;

    pthread_mutex_init(&the_mutex, NULL);
    pthread_cond_init(&condc, NULL);
    pthread_cond_init(&condp, NULL);

    pthread_create(&con, NULL, consumer, NULL);
    pthread_create(&pro, NULL, producer, NULL);

    pthread_join(pro, NULL);
    pthread_join(con, NULL);

    pthread_cond_destroy(&condc);
    pthread_cond_destroy(&condp);
    pthread_mutex_destroy(&the_mutex);

    return 0;
}