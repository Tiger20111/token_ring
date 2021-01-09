from plotly.offline import download_plotlyjs, init_notebook_mode, plot, iplot
import plotly
import plotly.graph_objs as go

def throughputOne(df):
  name1= 'Threads 6 with shift 5'
  trace1 = go.Scatter(
      x=df.index,
      y=df.throughputRing,
      name=name1
  )

  fig = go.Figure(data=[trace1])

  fig.update_layout(
      title="Throughput Chain by epoch time",
      yaxis_title="Throughput by sec",
      xaxis_title="number of epoch"
  )

  iplot(fig, show_link=False)

def throughputRing(df1):

  result = {}
  dif_threads = df1['numberThread'].unique()

  for thread in dif_threads:
    df_op = df1.loc[df1['numberThread'] == thread]
    result[thread] = df_op['throughputRing'].mean()

  trace1 = go.Scatter(
      x=list(result.keys()),
      y=list(result.values()),
  )


  fig = go.Figure(data=[trace1])

  fig.update_layout(
      title="Dependency throughput on length ring",
      yaxis_title="Throughput",
      xaxis_title="Threads"
  )

  iplot(fig, show_link=False)

def throughputChain(df1):

  result = {}
  dif_threads = df1['numberThread'].unique()

  for thread in dif_threads:
    df_op = df1.loc[df1['numberThread'] == thread]
    result[thread] = df_op['throughputRing'].mean() / (thread - 1)

  trace1 = go.Scatter(
      x=list(result.keys()),
      y=list(result.values()),
  )


  fig = go.Figure(data=[trace1])

  fig.update_layout(
      title="Dependency throughput on length ring",
      yaxis_title="Throughput",
      xaxis_title="Threads"
  )

  iplot(fig, show_link=False)

def latencyRing(df1):

  result = {}
  dif_threads = df1['numberThread'].unique()

  for thread in dif_threads:
    df_op = df1.loc[df1['numberThread'] == thread]
    result[thread] = df_op['latencyChain'].mean()

  trace1 = go.Scatter(
      x=list(result.keys()),
      y=list(result.values()),
  )

  fig = go.Figure(data=[trace1], layout={'title':'Latency Chain by time processing ring'})

  fig.update_layout(
        title="Dependency latency on number threads and shift",
        yaxis_title="Latency",
        xaxis_title="Threads(*)"
    )

  iplot(fig, show_link=True)

def calculateDependencyLatencyRing(df1, thread_min, thread_max):
  latency = {}
  koefs = {}
  dif_threads = df1['numberThread'].unique()

  for thread in dif_threads:
    df_op = df1.loc[df1['numberThread'] == thread]
    latency[thread] = df_op['latencyChain'].mean()

  for thread in dif_threads:
    if ((thread < thread_min) or (thread > thread_max)):
      continue
    koefs[thread] = (latency[thread]/(thread - 1))

  trace1 = go.Scatter(
      x=list(koefs.keys()),
      y=list(koefs.values()),
  )

  fig = go.Figure(data=[trace1])

  fig.update_layout(
        title="Dependency latency koef",
        yaxis_title="koef",
        xaxis_title="Threads(*)"
    )
  iplot(fig, show_link=True)
  print("Avarage: " + str(sum(koefs.values()) / len (koefs.values())))

def throughputRingDependencyShift(df1):

  result = {}
  dif_shift = df1['shift'].unique()

  for shift in dif_shift:
    df_op = df1.loc[df1['shift'] == shift]
    result[shift] = df_op['throughputRing'].mean()

  trace1 = go.Scatter(
      x=list(result.keys()),
      y=list(result.values()),
  )


  fig = go.Figure(data=[trace1])

  fig.update_layout(
      title="Dependency throughput on length ring",
      yaxis_title="Throughput",
      xaxis_title="Shift"
  )

  iplot(fig, show_link=False)

def latencyRingDependencyShift(df1):

  result = {}
  dif_shift = df1['shift'].unique()

  for shift in dif_shift:
    df_op = df1.loc[df1['shift'] == shift]
    result[shift] = df_op['latencyChain'].mean()

  trace1 = go.Scatter(
      x=list(result.keys()),
      y=list(result.values()),
  )


  fig = go.Figure(data=[trace1])

  fig.update_layout(
      title="Dependency throughput on length ring",
      yaxis_title="Latency",
      xaxis_title="Shift"
  )

  iplot(fig, show_link=False)