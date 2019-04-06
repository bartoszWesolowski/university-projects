package pl.enigmatic.edu.squares.squares.components;

import pl.enigmatic.edu.squares.statistics.PlayerStatistic;
import pl.enigmatic.edu.squares.statistics.Statistics;
import pl.enigmatic.edu.squares.statistics.StatisticsManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ShowStatsButton extends JButton implements ActionListener {

    public ShowStatsButton() {
        super("Stats");
        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        Statistics statistics = StatisticsManager.getInstance().getStatistics();
        List<PlayerStatistic> playerStatistics = statistics.getPlayerStatistics();
        StringBuilder stats = new StringBuilder("STATISTICS" + "\n");
        for (int i = 0; i < playerStatistics.size(); i++) {
            PlayerStatistic playerStatistic = playerStatistics.get(i);
            stats.append(i)
                    .append(". ")
                    .append(playerStatistic.getName())
                    .append(" (")
                    .append(playerStatistic.getTime())
                    .append(")")
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, stats.toString());

    }
}
