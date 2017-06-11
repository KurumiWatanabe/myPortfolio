#include <stdio.h>
#include <stdlib.h>
#include <limits.h>
#define MAXLENGTH 512
#define NOT 100


typedef struct {
	int dis;
	int fg;
}FD;

FD *fd; //確定の最短距離
int *td;	//仮の最短距離
int **edg_weight;//辺の重み
int max;


//ファイルから値を読み込む
//領域を確保する
void readValue(FILE *fin) {

	int n = 0, i = 0, j = 0, weight = 0;
	char buf[MAXLENGTH];

	//配列の大きさを読み込む（ファイルの一行目）
	if (fgets(buf, sizeof(buf), fin) != NULL) {
		sscanf(buf, "%d", &n);

		//数字が二個以上の時のエラー処理
		if (n<2) {
			printf("nodeの数の値が間違っています。\n");
			exit(1);
		}
	}
	max = n;
	//nodeの配列の領域を確保する（横軸）
	edg_weight = (int**)malloc(sizeof(int)*n);

	//nodeの配列の領域を確保する（縦軸）
	for (i = 0; i<n; i++) {
		edg_weight[i] = (int*)malloc(sizeof(int)*n);
	}

	fd = (FD*)malloc(sizeof(FD)*n);
	td = (int*)malloc(sizeof(int)*n);

	//配列nodeの初期化
	for (i = 0; i<n; i++)
	{
		for (j = 0; j < n; j++)
		{
			edg_weight[i][j] = 999;
		}
		fd[i].dis = 999;
		fd[i].fg = 0;
	}

	//ファイルを読み込む
	while (fgets(buf, sizeof(buf), fin) != NULL) {

		//ファイルから要素を読み込む
		if ((sscanf(buf, "%d%d%d", &i, &j, &weight)) != NULL) {
			edg_weight[i][j] = weight;
			edg_weight[j][i] = weight;
		}
	}
}

void updateDistance(int next_node) {//最小の重み) {
	int j = 0;

	for (j = 0; j<max; j++) {
		//最小値の更新
		if (td[j] >(edg_weight[next_node][j] + td[next_node])) {
			td[j] = edg_weight[next_node][j] + td[next_node];
		}
	}
}

//最小値を見つける
int  findMin() {
	int i;
	int min = 999;
	int min_index = 0;

	for (i = 0; i < max; i++) {
		//一番小さい値をminに格納
		if ((min>td[i]) && (fd[i].fg == 0)) {
			min = td[i];
			min_index = i;
		}
	}
	//値の更新
	fd[min_index].dis = min;
	fd[min_index].fg = 1;

	return min_index;
}

//pathの計算
int path(int next_node, int start) {
	int i = 0;
	int current;
	int pre_node;

	for (i = 0; i < max; i++) {
		//現在のnodeからつながっている辺の重みを引いて、次のノードを探す
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


	//ファイルの読み込み
	//値がないときのエラー
	if ((fin = fopen(fname, "r")) == NULL) {
		printf("Can't open file: %s\n", fname);
		return 1;
	}

	//ファイル読み込みと領域確保
	readValue(fin);

	//引数よりstart nodeとgoal nodeを読み込む
	start = max-max;
	goal = max-1;

	//引数の表示
	printf("Input file: %s\n", fname);
	printf("Starting node: %d\n", start);
	printf("Goal node: %d\n", goal);

	//nodeの初期化
	//最初のnodeをstartに設定する
	for (i = 0; i<max; i++) {
		td[i] = 999;
	}
	td[start] = 0;
	//fd[start].fg = 1;

	next_node = start;
	while (fd[goal].fg != 1) {
		//最小値の距離を求める
		next_node = findMin();
		//値をupdateする
		updateDistance(next_node);
		
		printf("Fixed node: %d,Distance from the starting node:%d\n", next_node, fd[next_node].dis);
	}
	//最短路の道のりの計算
	//スタートの表示
	printf("Shortest distance from node[%d] to node[%d]: %d\n", start, goal, fd[goal].dis);
	printf("node[%d]\n", start);
	path(goal, start);
	return 0;

}