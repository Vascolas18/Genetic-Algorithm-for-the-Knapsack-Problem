import pandas as pd
import matplotlib.pyplot as plt
from scipy.stats import kruskal

# Vasco Maria fc56374

filepathSeq = "C:/Users/Vascolas18/Desktop/56374-KnapsackGA/SeqTimesGA.csv"
filepathPar4N = "C:/Users/Vascolas18/Desktop/56374-KnapsackGA/ParalTimes4N.csv"
filepathPar4NP = "C:/Users/Vascolas18/Desktop/56374-KnapsackGA/ParalTimes4NP.csv"
filepathPar8N = "C:/Users/Vascolas18/Desktop/56374-KnapsackGA/ParalTimes8N.csv"
filepathPar8NP = "C:/Users/Vascolas18/Desktop/56374-KnapsackGA/ParalTimes8NP.csv"
filepathPar12N = "C:/Users/Vascolas18/Desktop/56374-KnapsackGA/ParalTimes12N.csv"
filepathPar12NP = "C:/Users/Vascolas18/Desktop/56374-KnapsackGA/ParalTimes12NP.csv"

dfSeq = pd.read_csv(filepathSeq)
df4N = pd.read_csv(filepathPar4N)
df4NP = pd.read_csv(filepathPar4NP)
df8N = pd.read_csv(filepathPar8N)
df8NP = pd.read_csv(filepathPar8NP)
df12N = pd.read_csv(filepathPar12N)
df12NP = pd.read_csv(filepathPar12NP)


coluna1_Seq = dfSeq['seconds']
coluna2_df4N = df4N['seconds']
coluna2_df4NP = df4NP['seconds']
coluna2_df8N = df8N['seconds']
coluna2_df8NP = df8NP['seconds']
coluna2_df12N = df12N['seconds']
coluna2_df12NP = df12NP['seconds']

plt.boxplot([coluna1_Seq, coluna2_df4N, coluna2_df4NP,coluna2_df8N ,coluna2_df8NP,coluna2_df12N,coluna2_df12NP], labels=['Seq','4N','4NP','8N','8NP','12N','12NP'])

plt.title('Sequentially vs Pararell 30 times')


plt.ylabel('Seconds')
plt.xlabel('P = with Phaser && N = Number of cores used')

plt.show()

#É PRECISO FECHAR A JANELA DO PLOT PARA CORRER O CODIGO A SEGUIR  

result = kruskal(coluna1_Seq, coluna2_df4N, coluna2_df4NP,coluna2_df8N ,coluna2_df8NP,coluna2_df12N,coluna2_df12NP)

# Exibe os resultados
print("Estatística de teste de Mann-Whitney U:", result.statistic)
print("Valor p:", result.pvalue)

# Analisa o valor de p
alpha = 0.05  # Nivel de significancia
if result.pvalue < alpha:
    print("Há diferenças significativas entre as colunas.")
else:
    print("Não há diferenças significativas entre as colunas.")




