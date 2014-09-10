package com.wordpress.nprogramming.parsing;

import com.wordpress.nprogramming.domain.Job;

import java.util.List;

public interface JobParser {
    List<Job> parse(String jil);
}
