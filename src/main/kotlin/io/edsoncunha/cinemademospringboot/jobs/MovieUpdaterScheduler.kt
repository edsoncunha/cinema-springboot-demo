package io.edsoncunha.cinemademospringboot.jobs

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TriggerContext
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.config.ScheduledTaskRegistrar
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors


@Configuration
@EnableScheduling
class MovieUpdaterScheduler(private val updaterJob: MovieUpdaterJob, private val clock: Clock) : SchedulingConfigurer {
    private val log = LoggerFactory.getLogger(MovieUpdaterScheduler::class.java)

    @Bean
    fun taskExecutor(): Executor {
        return Executors.newSingleThreadScheduledExecutor()
    }

    override fun configureTasks(taskRegistrar: ScheduledTaskRegistrar) {
        log.info("Configuring movie updater task")

        taskRegistrar.setScheduler(taskExecutor())

        val task = {
            updaterJob.updateAllMovies()
        }

        val nextExecutionDate: (TriggerContext) -> Date = { context: TriggerContext ->
            val lastCompletionTime = context.lastCompletionTime()

            val nextExecutionTime = if (lastCompletionTime == null) {
                clock.now().toInstant()
            } else {
                lastCompletionTime
                    .toInstant()
                    .plusMillis(updaterJob.getDelayInMilliseconds())
            }

            log.info("Next execution time will be $nextExecutionTime")

            Date.from(nextExecutionTime)
        }

        taskRegistrar.addTriggerTask(
            task,
            nextExecutionDate
        )
    }
}