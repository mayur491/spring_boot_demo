package com.codemayur.ipldashboard.data

import com.codemayur.ipldashboard.model.Match
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider
import org.springframework.batch.item.database.JdbcBatchItemWriter
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.sql.DataSource

@Configuration
@EnableBatchProcessing
class BatchConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    object BatchConfigConstants {
        val MATCH_INPUT_FIELD_NAMES = arrayOf(
            "id",
            "city",
            "date",
            "player_of_match",
            "venue",
            "neutral_venue",
            "team1",
            "team2",
            "toss_winner",
            "toss_decision",
            "match_winner",
            "result",
            "result_margin",
            "eliminator",
            "method",
            "umpire1",
            "umpire2"
        )
    }

    @Bean
    fun reader(): FlatFileItemReader<MatchInput> {

        val beanWrapperFieldSetMapper: BeanWrapperFieldSetMapper<MatchInput> = BeanWrapperFieldSetMapper()
        beanWrapperFieldSetMapper.setTargetType(MatchInput::class.java)

        return FlatFileItemReaderBuilder<MatchInput>()
            .name("MatchInputReader")
            .resource(ClassPathResource("match-data.csv"))
            .delimited()
            .names(*BatchConfigConstants.MATCH_INPUT_FIELD_NAMES)
            .fieldSetMapper(beanWrapperFieldSetMapper)
            .build()
    }

    @Bean
    fun processor(): MatchDataProcessor {
        return MatchDataProcessor()
    }

    @Bean
    fun writer(dataSource: DataSource): JdbcBatchItemWriter<Match> {
        return JdbcBatchItemWriterBuilder<Match>()
            .itemSqlParameterSourceProvider(BeanPropertyItemSqlParameterSourceProvider())
            .sql(
                "INSERT INTO match (id,city,date,player_of_match,venue,team1,team2,toss_winner,toss_decision,match_winner,result,result_margin,umpire1,umpire2) " +
                        "VALUES (:id,:city,:date,:playerOfMatch,:venue,:team1,:team2,:tossWinner,:tossDecision,:matchWinner,:result,:resultMargin,:umpire1,:umpire2)"
            )
            .dataSource(dataSource)
            .build()
    }

    @Bean
    fun importUserJob(listener: JobCompletionNotificationListener, step1: Step): Job {
        return jobBuilderFactory["importUserJob"]
            .incrementer(RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build()
    }

    @Bean
    fun step1(writer: JdbcBatchItemWriter<Match>): Step {
        return stepBuilderFactory["step1"]
            .chunk<MatchInput, Match>(10)
            .reader(reader())
            .processor(processor())
            .writer(writer)
            .build()
    }

}
