package com.wordpress.nprogramming;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Test_Parsing_Simple_Jil {

    private String jil;

    @Before
    public void setUp() throws Exception {
        Path sampleJilPath = Paths.get(getClass().getResource("/sample.jil").toURI());
        jil = new String(Files.readAllBytes(sampleJilPath));
    }

    @Test
    public void test_parsing_simple_jil_by_passing_path() throws Exception {
        final JobParser jobParser = new JobParserImpl();
        Job job = jobParser.parse(jil).get(0);

        assertThat(job.getJobName(), is("template"));
        assertThat(job.getJobType(), is(JobType.COMMAND));
        assertThat(job.getProperty("box_name"), is("box1"));
        assertThat(job.getProperty("command"), is("ls -l"));
        assertThat(job.getProperty("machine"), is("localhost"));
        assertThat(job.getProperty("owner"), is("lyota01@TANT-A01"));
        assertThat(job.getProperty("permission"), is("gx,ge,wx,we,mx,me"));
        assertThat(job.getProperty("date_conditions"), is("1"));
        assertThat(job.getProperty("days_of_week"), is("all"));
        assertThat(job.getProperty("start_times"), is("\"15:00, 14:00\""));
        assertThat(job.getProperty("run_window"), is("\"14:00 - 6:00\""));
        assertThat(job.getProperty("condition"), is("s(job1)"));
        assertThat(job.getProperty("description"), is("\"description field\""));
        assertThat(job.getProperty("n_retrys"), is("12"));
        assertThat(job.getProperty("term_run_time"), is("60"));
        assertThat(job.getProperty("box_terminator"), is("1"));
        assertThat(job.getProperty("job_terminator"), is("1"));
        assertThat(job.getProperty("std_out_file"), is("/tmp/std_out"));
        assertThat(job.getProperty("std_err_file"), is("/tmp/std_err"));
        assertThat(job.getProperty("min_run_alarm"), is("5"));
        assertThat(job.getProperty("max_run_alarm"), is("10"));
        assertThat(job.getProperty("alarm_if_fail"), is("1"));
        assertThat(job.getProperty("profile"), is("/tmp/.profile"));

        System.out.println(job.toString());
    }

}
