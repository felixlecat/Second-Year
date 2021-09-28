#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

struct student{ //struct for a student
    char name[20];
    char initial;
    char surname[20];
    int year;
    char coursename[5];
    char group;
    int score;
};

struct list_node{ //struct for a node
    void *data;

    struct list_node *next;
};

struct format_string{ //struct for a line of format string
    char data[20];
};

struct format_string *new_fs(char data[]){ //struct to create a new pointer to a format string
    struct format_string *fs;
    fs = malloc(sizeof(struct format_string));

    strcpy(fs->data, data);

    return fs;
}

struct list_node *new_node(void *data){ //struct to create a new pointer to a node
    struct list_node *n;
    n = malloc(sizeof(struct list_node));

    n->data = data;
    n->next = NULL;
    return n;
}

void append(struct list_node *list_head, struct list_node *node){ //function to add a new node at the end of the list
    struct list_node *cursor;
    cursor = list_head;
    while(cursor->next != NULL){
        cursor = cursor->next;
    }
    cursor->next = node;
    node->next = NULL;
}

struct list_node **first_item = NULL; //I could not get this pointer to point to the first linked list in read_line - I tried several methods but still fail

int read_line(FILE *f, char *s, struct list_node *n){ //scanf implementation
    char temp[20];
    char input;

    int i;
    int j = 0;
    int match_par = 0;

    struct format_string *fs;
    struct list_node head;
    struct list_node *cursor;

    cursor = &head;
    head.data = NULL;
    head.next = NULL;

    printf("Line read:\n");
    printf("%s\n", s); //print out the line read

    for(i=0;s[i]!='\0';i++){ //iterate through the format string
        switch(s[i]){
            case ' ':
                temp[j] = '\0';

                fs = new_fs(temp);
                cursor = new_node(fs);
                append(&head, cursor);

                if(isalpha(temp[0])){
                    if(temp[1]!='\0'){ //if the temp array only consist of one element(excluding null terminator)
                        printf("(string) ");
                    }
                    else{ //if the temp array has more than one element
                        printf("(char) ");
                    }
                }
                else if(isdigit(temp[0])){ //if the temp array consist of integer(s)
                    printf("(int) ");
                }

                printf("%s\n", temp);
                j = 0;
                match_par++; //increase the count of matched words of the format string
                break;
            case '(':
                temp[j] = '\0';
                break;
            case ')':
                temp[j] = '\0';
                break;
            default:
                temp[j] = s[i];
                j++;
                break;
        }
    }

    temp[j] = '\0'; //the last array of the format string

    fs = new_fs(temp);
    cursor = new_node(fs);
    append(&head, cursor);

    if(isalpha(temp[0])){
        if(temp[1]!='\0'){
            printf("(string) ");
        }
        else{
            printf("(char) ");
        }
    }
    else if(isdigit(temp[0])){
        printf("(int) ");
    }

    printf("%s\n", temp);
    j = 1;
    match_par++;

    printf("First linked list:\n"); //print out the (first) linked list
    cursor = &head;
    while(cursor->next != NULL){
        cursor = cursor->next;
        fs = (struct format_string *)cursor->data;
        printf("Node %d - %s\n", j, fs->data);
        j++;
    }

    return match_par; //return the number of matched parameter(s)
}

struct student *new_student(char *str){ // I have to use my second option which is re-read the line from the file - struct to create a new pointer to a student
    struct student *s;
    s = malloc(sizeof(struct student));

    char temp[25];
    char c;
    int x;

    int i;
    int j = 0;
    int word = 0;

    for(i=0;str[i]!='\0';i++){ //iterate through the format string
        switch(str[i]){
            case ' ':
                temp[j] = '\0';

                switch(word){
                    case 0: //add name
                        strcpy(s->name, temp);
                        break;
                    case 1: //add initial
                        c = temp[0];
                        s->initial = c;
                        break;
                    case 2: //add surname
                        strcpy(s->surname, temp);
                        break;
                    case 3: //add year
                        x = atoi(temp);
                        s->year = x;
                        break;
                    case 4: //add coursename
                        strcpy(s->coursename, temp);
                        break;
                    case 5: //add group
                        c = temp[0];
                        s->group = c;
                        break;
                }

                j = 0;
                word++;
                break;
            case '(':
                temp[j] = '\0';
                break;
            case ')':
                temp[j] = '\0';
                break;
            default:
                temp[j] = str[i];
                j++;
                break;
        }
    }

    //add score
    temp[j] = '\0';
    x = atoi(temp);
    s->score = x;
    word++;

    return s;
};



int main(){

    int reading = 1;
    int items_read;
    int i = 1;
    char test_line[100];
    char character;
    char filename[50];

    struct student *s;
    struct list_node student_head;
    struct list_node *student_cursor;

    student_cursor = &student_head;
    student_head.data = NULL;
    student_head.next = NULL;

    printf("Enter filename: "); //get filename from use input
    scanf("%s", filename);

    FILE *f;
    f = fopen(filename, "r");
    if(f == NULL){
        printf("File not found.\n");
        return 1;
    }

    while(reading){ //reading the file
        if(feof(f)){ //if reached end of file
            reading = 0;
            break;
        }

        while(fgets(test_line, 100, f) != NULL){
            items_read = read_line(f, test_line, &student_head);
            printf("Total number of items read: %d\n\n", items_read);
            if(items_read == 7){
                s = new_student(test_line);
                student_cursor = new_node(s);
                append(&student_head, student_cursor);
            }
        }
    }

    printf("Student List:\n"); //print out student list
    student_cursor = &student_head;
    while(student_cursor->next != NULL){
        student_cursor = student_cursor->next;
        s = (struct student *)student_cursor->data;
        printf("%d. %s (%c) %s %d %s %c %d\n", i, s->name, s->initial, s->surname, s->year, s->coursename, s->group, s->score);
        i++;
    }

    fclose(f);

    return 0;
}
