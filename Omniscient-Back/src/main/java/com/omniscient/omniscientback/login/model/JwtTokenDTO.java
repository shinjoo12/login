package com.omniscient.omniscientback.login.model;

public class JwtTokenDTO {

    String accessToken;
    String refreshToken;
    private long accessTokenExpiry;
    private long refreshTokenExpiry;

    public JwtTokenDTO() {
    }

    public JwtTokenDTO(String accessToken, String refreshToken, long accessTokenExpiry, long refreshTokenExpiry) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getAccessTokenExpiry() {
        return accessTokenExpiry;
    }

    public void setAccessTokenExpiry(long accessTokenExpiry) {
        this.accessTokenExpiry = accessTokenExpiry;
    }

    public long getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }

    public void setRefreshTokenExpiry(long refreshTokenExpiry) {
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    @Override
    public String toString() {
        return "JwtTokenDTO{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", accessTokenExpiry=" + accessTokenExpiry +
                ", refreshTokenExpiry=" + refreshTokenExpiry +
                '}';
    }
}
