library(ggplot2)
maxms <- 800
ib <- read.csv(file='importbench.csv', sep=',')
filtered <- ib[ib$ms <= maxms, ]
row.names(filtered) <- NULL
message(sprintf("Kept %d/%d (%.02f%%) rows with duration <= %d ms",
    nrow(filtered), nrow(ib), nrow(filtered)/nrow(ib) * 100, maxms))
png("importbench.png", width = 1280, height = 720)
ptitle <-paste(
    sprintf('Durations of %d importbench transactions under %d ms (%d outlier transactions of longer duration not shown)',
            nrow(filtered), maxms, nrow(ib) - nrow(filtered)),
    'Each tx contains 10k vertex additions',
    sep='\n')
print(qplot(filtered[,'txcount'],
    filtered[,'ms'],
    geom = 'point',
    main = ptitle,
    xlab = 'Cumulative vertices created since program start',
    ylab = 'Time to insert 10k vertices / commit 1 tx (ms)'))
dev.off()
