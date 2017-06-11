#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#define MAXLENGTH 512
#define NOT 100


typedef struct {
	int dis;
	int fg;
}FD;

FD *fd; //�m��̍ŒZ����
int *td;	//���̍ŒZ����
int **edg_weight;//�ӂ̏d��
int max;


//�t�@�C������l��ǂݍ���
//�̈���m�ۂ���
void readValue(FILE *fin) {

	int n = 0, i = 0, j = 0, weight = 0;
	char buf[MAXLENGTH];

	//�z��̑傫����ǂݍ��ށi�t�@�C���̈�s�ځj
	if (fgets(buf, sizeof(buf), fin) != NULL) {
		sscanf(buf, "%d", &n);

		//��������ȏ�̎��̃G���[����
		if (n<2) {
			printf("node�̐��̒l���Ԉ���Ă��܂��B\n");
			exit(1);
		}
	}
	max = n;
	//node�̔z��̗̈���m�ۂ���i�����j
	edg_weight = (int**)malloc(sizeof(int)*n);

	//node�̔z��̗̈���m�ۂ���i�c���j
	for (i = 0; i<n; i++) {
		edg_weight[i] = (int*)malloc(sizeof(int)*n);
	}

	fd = (FD*)malloc(sizeof(FD)*n);
	td = (int*)malloc(sizeof(int)*n);

	//�z��node�̏�����
	for (i = 0; i<n; i++)
	{
		for (j = 0; j < n; j++)
		{
			edg_weight[i][j] = 999;
		}
		fd[i].dis = 999;
		fd[i].fg = 0;
	}

	//�t�@�C����ǂݍ���
	while (fgets(buf, sizeof(buf), fin) != NULL) {

		//�t�@�C������v�f��ǂݍ���
		if ((sscanf(buf, "%d%d%d", &i, &j, &weight)) != NULL) {
			edg_weight[i][j] = weight;
			edg_weight[j][i] = weight;
		}
	}
}

void updateDistance(int next_node) {//�ŏ��̏d��) {
	int j = 0;

	for (j = 0; j<max; j++) {
		//�ŏ��l�̍X�V
		if (td[j] >(edg_weight[next_node][j] + td[next_node])) {
			td[j] = edg_weight[next_node][j] + td[next_node];
		}
	}
}

//�ŏ��l��������
int  findMin() {
	int i;
	int min = 999;
	int min_index = 0;

	for (i = 0; i < max; i++) {
		//��ԏ������l��min�Ɋi�[
		if ((min>td[i]) && (fd[i].fg == 0)) {
			min = td[i];
			min_index = i;
		}
	}
	//�l�̍X�V
	fd[min_index].dis = min;
	fd[min_index].fg = 1;

	return min_index;
}

//path�̌v�Z
int path(int next_node, int start) {
	int i = 0;
	int current;
	int pre_node;

	for (i = 0; i < max; i++) {
		//���݂�node����Ȃ����Ă���ӂ̏d�݂������āA���̃m�[�h��T��
		current = fd[next_node].dis - edg_weight[next_node][i];
		if (current == fd[i].dis)
		{
			path(i, start);
			printf("-> (%d) -> node[%d]\n", edg_weight[next_node][i], next_node);
		}
		if (current == start) {
			return 0;
		}
	}
}

int main() {

	FILE* fin;
	int start, goal;
	int i;
	int next_node;
	char fname[MAXLENGTH];

	printf("Input File Name: ");
	scanf("%s",&fname);


	//�t�@�C���̓ǂݍ���
	//�l���Ȃ��Ƃ��̃G���[
	if ((fin = fopen(fname, "r")) == NULL) {
		printf("Can't open file: %s\n", fname);
		return 1;
	}

	//�t�@�C���ǂݍ��݂Ɨ̈�m��
	readValue(fin);

	//�������start node��goal node��ǂݍ���
	start = max-max;
	goal = max-1;

	//�����̕\��
	printf("Input file: %s\n", fname);
	printf("Starting node: %d\n", start);
	printf("Goal node: %d\n", goal);

	//node�̏�����
	//�ŏ���node��start�ɐݒ肷��
	for (i = 0; i<max; i++) {
		td[i] = 999;
	}
	td[start] = 0;
	//fd[start].fg = 1;

	next_node = start;
	while (fd[goal].fg != 1) {
		//�ŏ��l�̋��������߂�
		next_node = findMin();
		//�l��update����
		updateDistance(next_node);
		
		printf("Fixed node: %d,Distance from the starting node:%d\n", next_node, fd[next_node].dis);
	}
	//�ŒZ�H�̓��̂�̌v�Z
	//�X�^�[�g�̕\��
	printf("Shortest distance from node[%d] to node[%d]: %d\n", start, goal, fd[goal].dis);
	printf("node[%d]\n", start);
	path(goal, start);
	return 0;

}