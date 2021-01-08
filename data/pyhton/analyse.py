from plotly.offline import download_plotlyjs, init_notebook_mode, plot, iplot
import plotly
import plotly.graph_objs as go

def throughputRing(df):
  df1 = df.loc[df['numberThread'] == 6]
  df2 = df.loc[df['numberThread'] == 10]
  df3 = df.loc[df['numberThread'] == 15]
  df4 = df.loc[df['numberThread'] == 20]

  df1 = df1.sort_values(by='timePrecessingRing')
  df2 = df2.sort_values(by='timePrecessingRing')
  df3 = df3.sort_values(by='timePrecessingRing')
  df4 = df4.sort_values(by='timePrecessingRing')

  trace1 = go.Scatter(
      x=df1.timePrecessingRing,
      y=df1.throughputChain * 6,
      name='Threads 6'
  )
  trace2 = go.Scatter(
      x=df2.timePrecessingRing,
      y=df2.throughputChain * 10,
      name='Threads 10'
  )
  trace3 = go.Scatter(
      x=df3.timePrecessingRing,
      y=df3.throughputChain * 15,
      name='Threads 15'
  )
  trace4 = go.Scatter(
      x=df4.timePrecessingRing,
      y=df4.throughputChain * 20,
      name='Threads 20'
  )


  fig = go.Figure(data=[trace1, trace2, trace3, trace4])

  fig.update_layout(
      title="Throughput Ring by time processing ring",
      yaxis_title="Number accepted messanges by one sec",
      xaxis_title="Time work ring (milliseconds)"
  )

  iplot(fig, show_link=False)


def latency(df, min_time, max_time):
  dfT = df.loc[(min_time <= df['timePrecessingRing']) & (df['timePrecessingRing'] < max_time)]

  df1 = dfT.loc[dfT['numberThread'] == 6]
  df2 = dfT.loc[dfT['numberThread'] == 10]
  df3 = dfT.loc[dfT['numberThread'] == 15]
  df4 = dfT.loc[dfT['numberThread'] == 20]

  df1 = df1.sort_values(by='timePrecessingRing')
  df2 = df2.sort_values(by='timePrecessingRing')
  df3 = df3.sort_values(by='timePrecessingRing')
  df4 = df4.sort_values(by='timePrecessingRing')

  trace1 = go.Scatter(
      x=df1.timePrecessingRing,
      y=df1.latencyChain,
      name='Threads6'
  )
  trace2 = go.Scatter(
      x=df2.timePrecessingRing,
      y=df2.latencyChain,
      name='Threads10'
  )
  trace3 = go.Scatter(
      x=df3.timePrecessingRing,
      y=df3.latencyChain,
      name='Threads15'
  )
  trace4 = go.Scatter(
      x=df4.timePrecessingRing,
      y=df4.latencyChain,
      name='Threads20'
  )

  fig = go.Figure(data=[trace1, trace2, trace3, trace4], layout={'title':'Latency Chain by time processing ring'})

  fig.update_layout(
        title="Latency Chain by time processing ring",
        yaxis_title="Time to deliver message (milliseconds)",
        xaxis_title="Time work ring (milliseconds)"
    )

  iplot(fig, show_link=True)

def throughputThreads(df1, df2, numThreads, min_time, max_time):
  df1 = df1.loc[(min_time <= df1['timePrecessingRing']) & (df1['timePrecessingRing'] < max_time)]
  df2 = df2.loc[(min_time <= df2['timePrecessingRing']) & (df2['timePrecessingRing'] < max_time)]

  df1 = df1.loc[df_1['numberThread'] == numThreads]
  df2 = df2.loc[df_2['numberThread'] == numThreads]

  df1 = df1.sort_values(by='timePrecessingRing')
  df2 = df2.sort_values(by='timePrecessingRing')

  name1= 'Threads ' + str(numThreads) + ' before opt'
  trace1 = go.Scatter(
      x=df1.timePrecessingRing,
      y=df1.throughputChain,
      name=name1
  )
  name2= 'Threads ' + str(numThreads) + ' after opt'
  trace2 = go.Scatter(
      x=df2.timePrecessingRing,
      y=df2.throughputChain,
      name=name2
  )


  fig = go.Figure(data=[trace1, trace2])

  fig.update_layout(
      title="Throughput Chain by time processing ring",
      yaxis_title="Number accepted messanges by one sec",
      xaxis_title="Time work ring (milliseconds)"
  )

  iplot(fig, show_link=False)

def latencyThreads(df1, df2, numThreads):
  df1 = df1.loc[df1['numberThread'] == numThreads]
  df2 = df2.loc[df2['numberThread'] == numThreads]

  df1 = df1.sort_values(by='timePrecessingRing')
  df2 = df2.sort_values(by='timePrecessingRing')

  name1= 'Threads ' + str(numThreads) + ' before opt'
  trace1 = go.Scatter(
      x=df1.timePrecessingRing,
      y=df1.latencyChain,
      name=name1
  )
  name2= 'Threads ' + str(numThreads) + ' after opt'
  trace2 = go.Scatter(
      x=df2.timePrecessingRing,
      y=df2.latencyChain,
      name=name2
  )


  fig = go.Figure(data=[trace1, trace2])

  fig.update_layout(
        title="Latency Chain by time processing ring",
        yaxis_title="Time to deliver message (milliseconds)",
        xaxis_title="Time work ring (milliseconds)"
    )

  iplot(fig, show_link=True)

def throughputThreadsShift(df_local, numThread1, num_message1):
  df1 = df_local.loc[(df_local['numberThread'] == numThread1) & (df_local['shift'] == shift1) & (df_local['numberMessages'] == num_message1)]

  df1 = df1.sort_values(by='shift')

  name1= 'Threads ' + str(numThread1) + ' and number of message ' + str(num_message1)
  trace1 = go.Scatter(
      x=df1.shift,
      y=df1.throughputRing,
      name=name1
  )

  fig = go.Figure(data=[trace1])

  fig.update_layout(
      title="Throughput with different shift",
      yaxis_title="Throughput",
      xaxis_title="Shift"
  )

  iplot(fig, show_link=False)

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